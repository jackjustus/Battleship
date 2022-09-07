import java.util.ArrayList;

import java.util.Scanner;
// Two player objects each with their own board
public class Player {

    private boolean attackAvailable;

    private translate t;

    private String name;

    private Scanner s;

    private Board b;

    private boolean numShipsGood;

    public Player() {

        t = new translate();

        numShipsGood = false;

        s = new Scanner(System.in);

        System.out.println("State your name:");
        name = s.next();
        System.out.println("Excellent.");

        //asks for number of ships player would like to play with
        //once valid num is given initializes Board "b" based on ships in Board class
        while (!numShipsGood) {
            try {
                System.out.println("How many ships would you like to play with?");
                if (s.nextInt() == 5)
                    System.out.println("Ok.");
                else
                    System.out.println("Too bad. Your playing with five.");
                b = new Board();
                numShipsGood = true;
            } catch (Error e) {
                System.out.println("Need to input int!");
            }
        }

    }

    public boolean attack() {
        int x;
        int y;
        b.printBoard();
        while (attackAvailable) {
            System.out.println("This is the board opponents. Now you will enter the coordinates \n of the space you would like to attack?");
            System.out.println("Which letter coordinate would you like to enter?");
            t.convert(s.nextLine());
        }
    }

        public boolean Sink () {
            return true;
        }
}
