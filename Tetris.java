package Tetris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class Tetris extends Thread {
    enum Move {LEFT, RIGHT};
    boolean lose = false;
    private int row = 10;
    private int col = 10;
    LinkedList<Form> forms = new LinkedList<>();
    ArrayList<Coord> coordsNotFree = new ArrayList<>();
    Form currentForm;

    public void run(){
        while(!lose){
            try {
                while(nextRowFree()){
                    sleep(400);
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

    public void gravity(){
        if(nextRowFree()){
            this.coordsNotFree.clear();
            this.currentForm.drop();
            this.checkCoords();
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
    //metodo per la rotazione
    //metodo per l'esplosione della riga completa

    public Tetris(){
        this.formGenerator();
        System.out.println(this.toString());
        this.currentForm = this.forms.getLast();
        this.checkCoords();
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
        if(m == Move.LEFT){
            for(int i = 0; i < coords.length; i++){
                if(coords[i].getY()-1 >= 0){
                    coords[i].setY(coords[i].getY()-1);
                }
            }
        }else if(m == Move.RIGHT){
            for(int i = 0; i < coords.length; i++){
                if(coords[i].getY()+1 <= this.col-1){
                    coords[i].setY(coords[i].getY()+1);
                }
            }
        }
        this.forms.remove(this.currentForm);
        this.currentForm.setCoords(coords);
        this.forms.add(this.currentForm);
    }

    /*public void rotate(){
        Coord [] coords = this.currentForm.getCoords();
        switch (this.currentForm.getName()){
            case 'I':{
                if(this.currentForm.getRotation() == Form.Rotation.NORMAL){
                    this.currentForm.setRotation(Form.Rotation.DEGREE90);

                }
            }
        }
    }*/

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
