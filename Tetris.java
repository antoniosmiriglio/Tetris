package Tetris;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tetris {
    private int row = 20;
    private int col = 10;
    ArrayList<Form> forms = new ArrayList<>();

    //public run(){
        //simula gravità
        //a muovi sinistra d muovi destra w ruota
    //}

    public void gravity(){
        for(Form f : this.forms){
            //gravity chiama un metodo di controllo dentro Form
            //Form controlla se la forma corrente non si trova sulla riga massima
            //controlla anche se lo spazio è libero
            //Form ha un metodo che controlla quale é l'elemento più in basso
            //metodo per la rotazione
            //metodo per lo spostamento destra o sinistra
            //metodo per l'esplosione della riga completa
        }
    }

    public Tetris(){
        this.formGenerator();
    }

    public void formGenerator(){
        int numb = ThreadLocalRandom.current().nextInt(7);
        System.out.println(numb);
        switch (numb){
            case 0: {
                this.forms.add(new Form('I', new Coord(0, 3), new Coord(0, 4), new Coord(0, 5), new Coord(0, 6)));
                break;
            }
            case 1: {
                this.forms.add(new Form('J', new Coord(0, 3), new Coord(1, 3), new Coord(1, 4), new Coord(1, 5)));
                break;
            }
            case 2:{
                this.forms.add(new Form('L', new Coord(0, 6), new Coord(1, 6), new Coord(1, 5), new Coord(1, 4)));
                break;
            }
            case 3:{
                this.forms.add(new Form('O', new Coord(0, 4), new Coord(0, 5), new Coord(1, 4), new Coord(1, 5)));
                break;
            }
            case 4: {
                this.forms.add(new Form('S', new Coord(0, 6), new Coord(0, 5), new Coord(1, 5), new Coord(1, 4)));
                break;
            }
            case 5: {
                this.forms.add(new Form('T', new Coord(0, 4), new Coord(1, 3), new Coord(1, 4), new Coord(1, 5)));
                break;
            }
            case 6: {
                this.forms.add(new Form('Z', new Coord(0, 3), new Coord(0, 4), new Coord(1, 4), new Coord(1, 5)));
                break;
            }
        }
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < this.row; i++){
            result += "[";
            for(int j = 0; j < this.col; j++){
                result += "[";
                for(Form f : this.forms){
                    if(f.compare(new Coord(i,j))){
                        result += "■";
                    }else{
                        result += " ";
                    }
                }
                result += "]";
            }
            result += "]\n";
        }
        return result;
    }
}
