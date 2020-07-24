package Tetris;


import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        char command = ' ';
        Tetris t = new Tetris();
        //t.start();
        System.out.println("A move left - D move right");
        //Quando si ruota una forma ne scompare un altra, e la forma corrente cambia colore e ogni tanto perde pezzi
        while(true){
            System.out.print("Insert command: ");
            command = scan.nextLine().charAt(0);
            if(command != ' '){
                if(command == 'a' || command == 'A'){
                    t.move(Tetris.Move.LEFT);
                }else if(command == 'd' || command == 'D'){
                    t.move(Tetris.Move.RIGHT);
                }else if(command == 's' || command == 'S'){
                    t.rotate();
                }
            }else{
                while(t.nextRowFree()){
                    t.gravity();
                }
                t.formGenerator();
                t.checkCoords();
            }
            System.out.println(t);
        }

    }
}
