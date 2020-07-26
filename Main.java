package Tetris;




import java.util.Scanner;

public class Main{
        public static void main(String [] args){
            Scanner scan = new Scanner(System.in);
            char command = ' ';
            Tetris t = new Tetris();
            t.start();
            System.out.println("A move left - D move right - S rotate");

            while(!t.getLose()){
                try{
                    command = scan.nextLine().charAt(0);
                    if(command == 'a' || command == 'A'){
                        t.move(Tetris.Move.LEFT);
                    }else if(command == 'd' || command == 'D'){
                        t.move(Tetris.Move.RIGHT);
                    }else if(command == 's' || command == 'S'){
                        t.rotate();
                    }
                    //t.gravity();
                }catch (StringIndexOutOfBoundsException e){
                    //t.gravity();
                    e.printStackTrace();
                }
                if(t.getLose()){
                    System.out.println("FAI SCHIFO");
                }
            }

        }
}

