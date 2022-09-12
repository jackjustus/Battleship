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
        //Initializes variables for player
        otherPlayerSunken = 0;
        t = new Translate();
        numShipsGood = false;
        s = new Scanner(System.in);

        //Gets players name
        System.out.println("State your name:");
        name = s.nextLine();
        System.out.println("Excellent.");

        //just for fun
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

    //takes coordinates from player and checks if it's a hit
    public void attack(Board b1) {
        boolean attackAvailable = true;
        boolean secondTime = false;
        int x = 0;
        int y = 0;
        int xTemp = -1;
        int yTemp = -1;
        int timesThru = 0;
        b.printBoard();

        //asks for coordinates of a space the player would like to guess
        while (attackAvailable) {
            //second time boolean for ease of play not functionallity
            timesThru = 0;
            if (secondTime) {

                //validates guess
                System.out.println("You already guessed that spot! Enter the coordinates \n of the space you would like to attack?");
                while (yTemp == -1) {
                    if (timesThru >= 1) {
                        System.out.println("You entered an invalid coordinate. Which letter coordinate for the row would you like to enter?");
                        yTemp = t.convert(s.nextLine());
                    } else {
                        System.out.println("Which letter coordinate for the row would you like to enter?");
                        yTemp = t.convert(s.nextLine());
                    }
                    timesThru++;
                }
                timesThru = 0;
                y = yTemp;
                while (xTemp == -1) {
                    if (timesThru >= 1) {
                        System.out.println("You entered an invalid coordinate. Which number coordinate for the column would you like to enter?");
                        xTemp = s.nextInt();
                    } else {
                        System.out.println("Which number coordinate for the column would you like to enter?");
                        xTemp = s.nextInt();
                    }
                    timesThru++;
                }
                timesThru = 0;
                x = xTemp;
                System.out.println("Which number coordinate for the column would you like to enter?");
                x = s.nextInt();

            }
            else {
                System.out.println("Please enter the coordinates \n of the space you would like to attack?");
                while (yTemp == -1) {
                    if (timesThru >= 1) {
                        System.out.println("You entered an invalid coordinate. Which letter coordinate for the row would you like to enter?");
                        yTemp = t.convert(s.nextLine());
                    } else
                        System.out.println("Which letter coordinate for the row would you like to enter?");
                    yTemp = t.convert(s.nextLine());
                }
                timesThru++;
            }
            timesThru = 0;
            y = yTemp;
            while (xTemp == -1) {
                if (timesThru >= 1) {
                    System.out.println("You entered an invalid coordinate. Which number coordinate for the column would you like to enter?");
                    xTemp = s.nextInt();
                } else {
                    System.out.println("Which number coordinate for the column would you like to enter?");
                    xTemp = s.nextInt();
                }
                timesThru++;
            }
            timesThru = 0;
            x = xTemp;
            x = s.nextInt();
        }


        //checks if the position the user guess is a hit, miss, or already guessed
        if (b.getSquares()[x][y] == 0) {
            System.out.println("Miss");
            attackAvailable = false;
        } else if (b.getSquares()[x][y] == -1) {
            System.out.println("You already guessed there silly. Guess again");
            secondTime = true;
        } else {
            System.out.println("HIT DADDY");
            b.hit(x, y);
            attackAvailable = false;
        }
    }
}
