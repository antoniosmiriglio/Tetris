package Tetris;


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



    public boolean compare(Coord c){
        for(int i = 0; i < this.coords.length; i++){
            if (this.coords[i].getY() == c.getY() && this.coords[i].getX() == c.getX()){
                return true;
            }
        }
        return false;
    }
}
