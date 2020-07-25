import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Tetris extends Thread {
    enum Move {LEFT, RIGHT};
    boolean lose = false;
    private int row = 10;
    private int col = 10;
    private LinkedList<Form> forms = new LinkedList<>();
    private ArrayList<Coord> coordsNotFree = new ArrayList<>();
    private Form currentForm;
    private Coord c;
    private Coord[] arrayCoords=new Coord[4];
    private Coord[] rotatedCoord;
    private boolean free=false;

    public void run(){
        while(!lose){
            try {
                while(nextRowFree()){
                    sleep(2000);
                    this.gravity();
                    System.out.println(this.toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.formGenerator();
                if(this.checkLose()){
                    System.out.println("Fai schifo");
                    break;
                }
                this.checkCoords();
                System.out.println(this.toString());
            }
        }
        //simula gravità
        //a muovi sinistra d muovi destra w ruota
    }

    public Tetris(){
        this.formGenerator();
        System.out.println(this.toString());
        this.currentForm = this.forms.getLast();
        this.c = this.currentForm.getCoordMin();
        this.checkCoords();
    }

    public void gravity(){
        if(nextRowFree()){
            this.coordsNotFree.clear();
            this.currentForm.drop();
            this.checkCoords();
            this.c = this.currentForm.getCoordMin();
        }
    }

    public boolean nextRowFree(){
        Coord c;
        boolean free = false;
        int count = 0;
        int xMax = this.currentForm.getMaxX();
        ArrayList<Integer> arrY = this.currentForm.getArrY();
        if(xMax < this.row-1){
            for(Integer i : arrY){
                c = new Coord(xMax+1, i);
                if(this.coordsNotFree.contains(c)){
                    break;
                }else{
                    count++;
                }

            }
        }
        if(count == arrY.size()){
            free = true;
        }
        return free;
    }

    public void checkCoords(){
        for(int i = 0; i < this.row; i++){
            for(int j = 0; j < this.col; j++){
                for(Form f : this.forms){
                    if(f.compare(new Coord(i,j))){
                        this.coordsNotFree.add(new Coord(i,j));
                    }
                }
            }
        }
    }

    public void formGenerator(){
        int numb = ThreadLocalRandom.current().nextInt(7);
        System.out.println(numb);
        switch (numb){
            case 0: {
                this.forms.add(new Form('I', new Coord(0, 3), new Coord(0, 4), new Coord(0, 5), new Coord(0, 6), "\033[0;34m■\033[0m"));
                break;
            }
            case 1: {
                this.forms.add(new Form('J', new Coord(0, 3), new Coord(1, 3), new Coord(1, 4), new Coord(1, 5), "\033[0;31m■\033[0m"));
                break;
            }
            case 2:{
                this.forms.add(new Form('L', new Coord(0, 6), new Coord(1, 6), new Coord(1, 5), new Coord(1, 4), "\033[0;35m■\033[0m"));
                break;
            }
            case 3:{
                this.forms.add(new Form('O', new Coord(0, 4), new Coord(0, 5), new Coord(1, 4), new Coord(1, 5), "\033[0;36m■\033[0m"));
                break;
            }
            case 4: {
                this.forms.add(new Form('S', new Coord(0, 6), new Coord(0, 5), new Coord(1, 5), new Coord(1, 4), "\033[0;33m■\033[0m"));
                break;
            }
            case 5: {
                this.forms.add(new Form('T', new Coord(0, 4), new Coord(1, 3), new Coord(1, 4), new Coord(1, 5), "\033[0;32m■\033[0m"));
                break;
            }
            case 6: {
                this.forms.add(new Form('Z', new Coord(0, 3), new Coord(0, 4), new Coord(1, 4), new Coord(1, 5), "\033[0;30m■\033[0m"));
                break;
            }
        }
        this.currentForm = this.forms.getLast();
    }

    public void move(Move m){
        Coord [] coords = this.currentForm.getCoords();
        int maxY = this.currentForm.getMaxY();
        int minY = this.currentForm.getMinY();
        if(m == Move.LEFT && minY-1 >= 0){
            for(int i = 0; i < coords.length; i++){
                if(coords[i].getY()-1 >= 0){
                    coords[i].setY(coords[i].getY()-1);
                }
            }
        }else if(m == Move.RIGHT && maxY+1 <= this.col-1){
            for(int i = 0; i < coords.length; i++){
                if(coords[i].getY()+1 <= this.col-1){
                    coords[i].setY(coords[i].getY()+1);
                }
            }
        }
        this.forms.remove(this.currentForm);
        this.currentForm.setCoords(coords);
        this.c = this.currentForm.getCoordMin();
        this.forms.add(this.currentForm);
    }

    public void rotate(){
        Coord [] coords = this.currentForm.getCoords();
        arrayCoords[0]=c;
        removeCurrentForm();
        switch (this.currentForm.getRotation()){
            case NORMAL:{
                singleRotate90();
                break;
            }
            case DEGREE90:{
                singleRotate180();
                break;
            }
            case DEGREE180:{
                singleRotate270();
                break;
            }
            case DEGREE270:{
                singleRotateNormal();
                break;
            }

        }
    }

    public void removeCurrentForm(){
        for (Coord c: this.currentForm.getCoords()){
            this.coordsNotFree.remove(c);
        }
    }

    private void setSingleRotatedCoord(int index,int x,int y){
        if (!this.coordsNotFree.contains(new Coord(c.getX()+x, c.getY()+y))){ //sembra esserci un errore nel girare la z dopo spostamento destra
            //la prima volta non entra in questa condizione, le altre si, probabilmente trova la prima coordinata occupata
            //dopo che sposti due volte a destra entra nella condizione solo una volta, e forma una L
            arrayCoords[index]=new Coord(c.getX()+x, c.getY()+y);
            free=true;
        } else{
            free=false;
        }
    }

    private void update(){
        if (free){
            this.forms.remove(this.currentForm);
            this.currentForm.setCoords(arrayCoords);
            switch (this.currentForm.getRotation()){
                case NORMAL:{
                    this.currentForm.setRotation(Form.Rotation.DEGREE90);
                    break;
                }
                case DEGREE90:{
                    this.currentForm.setRotation(Form.Rotation.DEGREE180);
                    break;
                }
                case DEGREE180:{
                    this.currentForm.setRotation(Form.Rotation.DEGREE270);
                    break;
                }
                case DEGREE270:{
                    this.currentForm.setRotation(Form.Rotation.NORMAL);
                    break;
                }

            }
            //if (currentForm.getName()!='T'){
                this.c = this.currentForm.getCoordMin();
            //}
            this.forms.add(this.currentForm);
            this.coordsNotFree.clear();
            this.checkCoords();
        }
    }

    private void caseZ() {
        rotatedCoord= new Coord[]{new Coord(1, 0),new Coord(1, -1),new Coord(2, -1)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    private void caseS() {
        rotatedCoord= new Coord[]{new Coord(1, 0),new Coord(1, 1),new Coord(2, 1)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    private void caseI() {
        rotatedCoord= new Coord[]{new Coord(1, 0),new Coord(2, 0),new Coord(3, 0)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    public void singleRotate90(){
        switch (this.currentForm.getName()){
            case 'I':{
                caseI();
                break;
            }
            case 'T':{
                rotatedCoord= new Coord[]{new Coord(1, 1),new Coord(1, 0),new Coord(2, 0)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'S':{
                caseS();
                break;
            }
            case 'Z':{
                caseZ();
                break;
            }
            case 'J':{
                rotatedCoord= new Coord[]{new Coord(0, 2),new Coord(0, 1),new Coord(1, 1),new Coord(2, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'L':{
                rotatedCoord= new Coord[]{new Coord(0, -1),new Coord(1, -1),new Coord(2, -1),new Coord(2, 0)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
        }
        update();
    }

    private void caseZ2() {
        rotatedCoord= new Coord[]{new Coord(0, 1),new Coord(1, 1),new Coord(1, 2)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    private void caseS2() {
        rotatedCoord= new Coord[]{new Coord(0, 1),new Coord(1, 0),new Coord(1, -1)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    private void caseI2() {
        rotatedCoord= new Coord[]{new Coord(0, 1),new Coord(0, 2),new Coord(0, 3)};
        for (int i=0;i<rotatedCoord.length;i++){
            setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
            if (!free)break;
        }
        return;
    }

    public void singleRotate180(){
        switch (this.currentForm.getName()){
            case 'I':{
                caseI2();
                break;
            }
            case 'T':{
                rotatedCoord= new Coord[]{new Coord(1, 0),new Coord(0, 1),new Coord(0, -1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'S':{
                caseS2();
                break;
            }
            case 'Z':{
                caseZ2();
                break;
            }
            case 'J':{
                rotatedCoord= new Coord[]{new Coord(2, 1),new Coord(1, -1),new Coord(1, 0),new Coord(1, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'L':{
                rotatedCoord= new Coord[]{new Coord(1, -1),new Coord(1, 0),new Coord(1, 1),new Coord(2, -1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
        }
        update();
    }

    // La Z e la T sono buggate come gesu
    //Controllare se le coordinate escono fuori dalla griglia
    //metodo esplosione riga

    public void singleRotate270(){
        switch (this.currentForm.getName()){
            case 'I':{
                caseI();
                break;
            }
            case 'S':{
                caseS();
                break;
            }
            case 'Z':{
                caseZ();
                break;
            }
            case 'T':{
                rotatedCoord= new Coord[]{new Coord(0, 1),new Coord(1, 0),new Coord(2, 1),new Coord(1, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'J':{
                rotatedCoord= new Coord[]{new Coord(-1, 1),new Coord(0, 1),new Coord(1, 1),new Coord(1, 0)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'L':{
                rotatedCoord= new Coord[]{new Coord(-1, 0),new Coord(-1, 1),new Coord(0, 1),new Coord(1, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
        }
        update();
    }

    public void singleRotateNormal(){
        switch (this.currentForm.getName()){
            case 'I':{
                caseI2();
                break;
            }
            case 'S':{
                caseS2();
                break;
            }
            case 'Z':{
                caseZ2();
                break;
            }
            case 'T':{
                rotatedCoord= new Coord[]{new Coord(1, -1),new Coord(1, 0),new Coord(1, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i+1, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'J':{
                rotatedCoord= new Coord[]{new Coord(0, -1),new Coord(1, -1),new Coord(1, 0),new Coord(1, 1)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
            case 'L':{
                rotatedCoord= new Coord[]{new Coord(0, 2),new Coord(1, 2),new Coord(1, 1),new Coord(1, 0)};
                for (int i=0;i<rotatedCoord.length;i++){
                    setSingleRotatedCoord(i, rotatedCoord[i].getX(), rotatedCoord[i].getY());
                    if (!free)break;
                }break;
            }
        }
        update();
    }

    public boolean checkLose(){
        Coord[] coords = this.currentForm.getCoords();
        if(this.coordsNotFree.contains(coords[0])
                || this.coordsNotFree.contains(coords[1])
                || this.coordsNotFree.contains(coords[2])
                || this.coordsNotFree.contains(coords[3])){
            this.lose = true;
            return true;
        }
        return false;
    }

    public String toString(){
        String result = "";
        boolean found = false;
        result += "╔══════════════════════════════╗\n";
        result += "╠══════════════════════════════╣\n";
        for(int i = 0; i < this.row; i++){
            result += "║";
            for(int j = 0; j < this.col; j++){
                result += "[";
                for(Form f : this.forms){
                    if(f.compare(new Coord(i,j))){
                        result += f.getColor();
                        found = true;
                        break;
                    }else{
                        found = false;
                    }
                }
                if(!found){
                    result += " ";
                }
                result += "]";
            }
            result += "║\n";
        }
        result += "╚══════════════════════════════╝\n";
        return result;
    }
}