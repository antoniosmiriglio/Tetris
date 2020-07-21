package Tetris;

import java.util.Arrays;

public class Form {
    private char name;
    Coord[] coords = new Coord[4];

    public Form(char name, Coord c1, Coord c2, Coord c3, Coord c4){
        this.name = name;
        this.coords[0] = c1;
        this.coords[1] = c2;
        this.coords[2] = c3;
        this.coords[3] = c4;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coord){
            Coord c = (Coord) o;
            for(int i = 0; i < this.coords.length; i++){
                if(this.coords[i].getX() == c.getX() && this.coords[i].getY() == c.getY()){
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coords);
    }
}
