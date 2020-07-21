package Tetris;



import java.util.ArrayList;

public class Form {
    private char name;
    Coord[] coords = new Coord[4];
    int xMax;
    ArrayList<Integer> arrY = new ArrayList<>();

    public Form(char name, Coord c1, Coord c2, Coord c3, Coord c4){
        this.name = name;
        this.coords[0] = c1;
        this.coords[1] = c2;
        this.coords[2] = c3;
        this.coords[3] = c4;
    }

    public boolean compare(Coord c){
        for(int i = 0; i < this.coords.length; i++){
            if (this.coords[i].getY() == c.getY() && this.coords[i].getX() == c.getX()){
                return true;
            }
        }
        return false;
    }

    public int getMaxX(){
        xMax = this.coords[0].getX();
        for(int i = 1; i < this.coords.length; i++){
            if(this.coords[i].getX() >= xMax){
                xMax = this.coords[i].getX();
                this.arrY.add(this.coords[i].getY());
            }
        }
        return xMax;
    }

    public ArrayList<Integer> getArrY(){
        return this.arrY;
    }

    public void drop(){
        for(int i = 0; i < this.coords.length; i++){
            this.coords[i].setX(this.coords[i].getX()+1);
        }
    }
}
