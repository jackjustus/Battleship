import java.sql.SQLOutput;
import java.util.Scanner;
public class Player {


    private Translate t;
    private String name;
    private Scanner s;
    private Board b;
    //checks amount of opponents ships sunken
    private int otherPlayerSunken;


    private boolean numShipsGood;

    public Player(String computer) {
        //Initializes variables for computer
        numShipsGood = false;
        s = new Scanner(System.in);
        name = computer;
        b = new Board();
        numShipsGood = true;
        otherPlayerSunken = 0;
    }

    public Player() {
        //Initializes variables for player
        otherPlayerSunken = 0;
        t = new Translate();
        numShipsGood = false;
        s = new Scanner(System.in);

        //Gets players name
        System.out.println("State your name:");
        name = s.nextLine();
        System.out.println("Excellent.");

        //just for jokes
        //asks for number of ships player would like to play with
        //once valid num is given initializes Board "b" based on ships in Board class
        while (!numShipsGood) {
            try {
                System.out.println("How many ships would you like to play with?");
                if (s.nextInt() == 5)
                    System.out.println("Ok.");
                else
                    System.out.println("Too bad. You're playing with five.");
                b = new Board();
                numShipsGood = true;
            } catch (Error e) {
                System.out.println("Need to input int!");
            }
        }

    }

    public Board getBoard() {
        return b;
    }

    //takes coordinates from player and checks if it's a hit, miss, or already guessed
    public void attack(Board b1) {
        boolean attackAvailable = true;
        int x = 0;
        int y = 0;
        b.printBoard();

        //while attackAvailable is true, the player attacks
        while (attackAvailable) {
                //asks for coordinates of a space the player would like to guess and validates their guess
                //takes y coordinate (letter)
                System.out.println(">>Enter the letter coordinate for the row you wish to attack");
                // Scanner Bugfix
                s.nextLine();
                y = Translate.convert(s.nextLine());

                while (y == -1) {
                    System.out.println(">>Invalid");
                    System.out.println(">>Enter what letter coordinate you wish to attack");
                    y = Translate.convert(s.nextLine());
                }




            //checks if the position the user guessed is a hit, miss, or already guessed
            //miss, user doesn't get to guess again
            if (b1.getIntSquares()[y][x] == 0) {
                System.out.println("Miss");
                b1.shoot(y, x);
                attackAvailable = false;
            }

            //if user already guessed this spot, then they guess again
            else if (b1.getIntSquares()[y][x] == -1) {
                System.out.println("You already guessed there silly. Guess again");
            }

            //user hits a part of the ship and gets to guess again
            else {
                System.out.println("JUSTUS HAS BEEN SERVED. (Hit)");
                b1.shoot(y, x);

                //checks if a ship will sink
                if (b1.checkSunk(y, x, b1)) {
                    System.out.println("SUNK!");
                    otherPlayerSunken++;
                }
//                if (otherPlayerSunken == 5){
//                    System.out.println("Win");
//                    attackAvailable = false;
//                }
                System.out.println("Guess again");
            }
        }
    }
}

