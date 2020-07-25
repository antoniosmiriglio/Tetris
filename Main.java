import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        char command = ' ';
        Tetris t = new Tetris();
        //t.start();
        System.out.println("A move left - D move right");
        //Quando si ruota una forma ne scompare un altra, e la forma corrente cambia colore e ogni tanto perde pezzi
        //La Z e la T sono buggate
        //la rotazione mangia le forme gia esistenti
        while(true){
            System.out.print("Insert command: \n");
            try{
                command = scan.nextLine().charAt(0);
                if(command != ' '){
                    if(command == 'a' || command == 'A'){
                        t.move(Tetris.Move.LEFT);
                    }else if(command == 'd' || command == 'D'){
                        t.move(Tetris.Move.RIGHT);
                    }else if(command == 's' || command == 'S'){
                        t.rotate();
                    }else {
                        System.out.println("mossa non valida");
                    }
                }else{
                    t.formGenerator();
                    t.checkCoords();
                }
                nextRowCheckAndGravity(t);

            }catch (StringIndexOutOfBoundsException e){
                nextRowCheckAndGravity(t);
                System.out.println("mossa non valida");
            }

            System.out.println(t);
        }

    }

    private static void nextRowCheckAndGravity(Tetris t){
        if(t.nextRowFree()){
            t.gravity();
        }else{
            t.formGenerator();
        }
    }
}

