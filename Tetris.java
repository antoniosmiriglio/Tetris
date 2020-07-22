package Tetris;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tetris extends Thread {
    private int row = 20;
    private int col = 10;
    ArrayList<Form> forms = new ArrayList<>();

    /*public void run(){
        try {
            int i=0;
            while (i<20){
                sleep(400);
                this.gravity();
                System.out.println(this.toString());
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.formGenerator();
            System.out.println(this.toString());
        }
        //simula gravità
        //a muovi sinistra d muovi destra w ruota
    }*/

    public void gravity(){
        Coord c;
        boolean freeCoords = false;
        for(Form f : this.forms){
            int xMax = f.getMaxX();
            ArrayList<Integer> arrY = f.getArrY();
            if(xMax < this.row-1){
                for(Integer i : arrY){
                    c = new Coord(xMax+1, i);
                    if(f.compare(c)){
                        freeCoords = false;
                        break;
                    }else{
                        freeCoords = true;
                    }
                    if(freeCoords){
                        f.drop();
                    }
                }
            }
        }
        if (freeCoords){
            this.formGenerator();
        }
    }
    //gravity chiama un metodo di controllo dentro Form
    //Form controlla se la forma corrente non si trova sulla riga massima
    //controlla anche se lo spazio è libero
    //Form ha un metodo che controlla quale é l'elemento più in basso
    //metodo per la rotazione
    //metodo per lo spostamento destra o sinistra
    //metodo per l'esplosione della riga completa

    public Tetris(){
        this.formGenerator();
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
                this.forms.add(new Form('Z', new Coord(0, 3), new Coord(0, 4), new Coord(1, 4), new Coord(1, 5), "\033[0;36m■\033[0m"));
                break;
            }
        }
    }

    public String toString(){
        String result = "";
        boolean found = false;
        for(int i = 0; i < 6; i++){
            result += "[";
            for(int j = 0; j < this.col; j++){
                result += "[";
                for(Form f : this.forms){
                    if(f.compare(new Coord(i,j))){
                        result += f.getColor();
                        found = true;
                        break;
                        //result += f.getIcon()//dare colori differenti alle forme e ritornarle
                    }else{
                        found = false;
                    }
                }
                if(!found){
                    result += " ";
                }
                result += "]";
            }
            result += "]\n";
        }
        return result;
    }
}
