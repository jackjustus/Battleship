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
        System.out.print("State your name: \n >> ");
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
                    System.out.println("Too bad. You're playing with " + Board.NUM_SHIPS);
                //TODO: REMOVE THIS DEBUG MODE WHEN FINISHED
                b = new Board(true);
                numShipsGood = true;
            } catch (Error e) {
                System.out.println("Need to input int!");
            }
        }

    }

    //checks how many ships have sunk
    public int getOtherPlayerSunken() {
        return otherPlayerSunken;
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
            print("\n\n\n");
            b.printBoard(true);


            //takes x coordinate (number) and validates
            System.out.print("Hello " + name + ", enter the [x] coordinate you want to attack \n >> ");
            x = safeNextInt();

            while (x > 10 || x < 1) {
                System.out.println(">>Invalid");
                System.out.println(name + ", enter the [x] coordinate you want to attack \n >> ");
                x = safeNextInt();
            }


            //takes y coordinate (letter) and validates
            System.out.print(name + ", enter the [y] coordinate you want to attack \n >>");
            // Scanner Bugfix
            s.nextLine();
            y = Translate.convert(s.nextLine());

            while (y == -1) {
                System.out.println("Invalid");
                System.out.println(name + ", enter the [y] coordinate you want to attack \n >>");
                y = Translate.convert(s.nextLine());
            }


            //checks if the position the user guessed is a hit, miss, or already guessed
            //miss, user doesn't get to guess again and the other player guesses
            if (b1.getIntSquares()[y - 1][x - 1] == 0) {
                System.out.println("Your shot missed");
                b1.shoot(y - 1, x - 1);
                attackAvailable = false;
            }

            //if user already guessed this spot, then they guess again
            else if (b1.getIntSquares()[y - 1][x - 1] == -1) {
                System.out.println("You already guessed there silly. Guess again \n >>");
            }

            //user hits a part of the ship and gets to guess again
            else {
                System.out.println("JUSTUS HAS BEEN SERVED. (Hit)");
                b1.shoot(y - 1, x - 1);

                //checks if a ship will sink and adds 1 if the ship sinks
                if (b1.checkSunk(y - 1, x - 1)) {
                    System.out.println("SUNK!");

                    otherPlayerSunken++;
                }

                //user guesses again if they didn't miss
                System.out.println(name + ", guess again");
            }

            // This clears the screen to allow for a clean handoff of the device
            if (!attackAvailable) {

                // Once the user has been informed about the result of their action, we ask them to hand the computer over and press enter to confirm the other player is ready
                System.out.println(name + " \n\nPlease press enter when you are ready to clear the console");
                s.nextLine();
                b.clearConsole();

                System.out.println(name + ", press enter when you are ready to show your information");
                s.nextLine();
                s.nextLine();

            }
        }
    }

    private String safeNextLine() {
        // The point of this is to use s.nextLine() but not throw an error


        boolean validInput = false;
        String text = "";
        //todo fix

        while (!validInput) {

            // This try catch catches any errors that scanner throws
            try {

                text = s.nextLine();

//                // This makes sure the input is not a number
//                try {
//
//                    // If this throws an error, then we know its not a number.
//                    Integer.parseInt(text);
//
//                }

                validInput = true;
            } catch (Exception e) {
                print("\nSorry, but that input is not valid. Please try again\n");


            }

        }

        return text;
    }

    private int safeNextInt() {

        // The point of this is to use s.nextLine() but not throw an error


        boolean validInput = false;
        int text = -999;
        //todo fix

        while (!validInput) {

            // This try catch catches any errors that scanner throws
            try {

                text = s.nextInt();


                if (text != -999)
                    validInput = true;
            } catch (Exception e) {
                print("\nSorry, but that input is not valid. Please try again\n");
                s.nextLine();
            }

        }

        return text;


    }

    // Simplified Printing
    private void print(String s) {
        System.out.print(s);
    }

    private void print(int i) {
        System.out.print(i);
    }

    private void clearConsole() {
        for (int i = 0; i < 40; i++)
            print("\n");
    }


}

