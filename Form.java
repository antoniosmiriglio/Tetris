package Tetris;

import java.util.ArrayList;

public class Form {
    enum Rotation{NORMAL, DEGREE90, DEGREE180, DEGREE270}
    private char name;
    Coord[] coords = new Coord[4];
    String color;
    int xMax;
    private Rotation rotation = Rotation.NORMAL;


    public Form(char name, Coord c1, Coord c2, Coord c3, Coord c4, String color){
        this.name = name;
        this.coords[0] = c1;
        this.coords[1] = c2;
        this.coords[2] = c3;
        this.coords[3] = c4;
        this.color = color;
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
            if(this.coords[i].getX() > xMax){
                xMax = this.coords[i].getX();
            }
        }
        return xMax;
    }

    public int getMaxY(){
        int yMax = this.coords[0].getY();
        for(int i = 1; i < this.coords.length; i++){
            if(this.coords[i].getY() > yMax){
                yMax = this.coords[i].getY();
            }
        }
        return yMax;
    }

    public int getMinY(){
        int yMin = this.coords[0].getY();
        for(int i = 1; i < this.coords.length; i++){
            if(this.coords[i].getY() < yMin){
                yMin = this.coords[i].getY();
            }
        }
        return yMin;
    }

    public Coord getCoordMin() {
        Coord coordMin=new Coord(this.coords[0].getX(),this.coords[0].getY());
        for(int i = 1; i < this.coords.length; i++){
            if(this.coords[i].getX() <= coordMin.getX() &&
                    this.coords[i].getY() < coordMin.getY()){
                coordMin = this.coords[i];
            }
        }
        return coordMin;
    }

    public ArrayList<Integer> getArrY(){
        ArrayList<Integer> arrY = new ArrayList<>();
        for (int i = 0; i < this.coords.length; i++){
            if(this.coords[i].getX() == this.xMax){
                arrY.add(this.coords[i].getY());
            }
        }
        return arrY;
    }

    public Coord[] getCoords(){
        return this.coords;
    }

    public void setCoords(Coord[] coords){
        this.coords = coords;
    }


    public Rotation getRotation(){
        return this.rotation;
    }

    public void setRotation(Rotation rotation){
        this.rotation = rotation;
    }

    public char getName(){
        return this.name;
    }

    public void drop(){
        for(int i = 0; i < this.coords.length; i++){
            this.coords[i].setX(this.coords[i].getX()+1);
        }
    }

    public String getColor(){
        return this.color;
    }
}
