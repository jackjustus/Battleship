import java.util.ArrayList;
import java.util.Scanner;

// Written by Jack Justus
public class Board {

    // Class variables
    private boolean[] squares;
    private ArrayList<Ship> ships;

    // Initialized in the initializer, same every game
    private int[] shipLengths;

    // Num Ships constant
    static final int NUM_SHIPS = 5;

    Scanner s;

    // Initializing the Board object, takes in the numShips (constant set in game class)
    // Runs through a for loop initializing each ship with the player's input
    public Board() {

        // lengths of the ships
        shipLengths = new int[]{5, 4, 3, 3, 2};

        // Make scanner
        s = new Scanner(System.in);

        // Initializing Ship objects using player input
        print("Time to make your ships!\n\n");
        for (int i = 0; i < NUM_SHIPS; i++) {

            // Ship(int length, int x, int y, boolean isVertical)
            print("This ship has a length of " + shipLengths[i] + ".\n");
            print("Where would you like to place it?\n");
            int x = getXValue();
            ship = new Ship();

        }

    }

    private int getXValue() {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int xValue = 0;

        while (!inputValid)
            try {
                print("Please type the x position in a single digit integer from 1-10\n");
                xValue = s.nextInt();
                inputValid = true;
            } catch (Exception e) {
            }
//                    e.printStackTrace();
        return xValue;

    }

    private int getYValue() {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int yValue = 0;

        // The Y value is inputted as a character, so we need this before we can convert it to a number
        String tempYValue;

        while (!inputValid)
            try {
                print("Please type the y position in a single letter from A-J\n");
                tempYValue = s.nextLine();
                inputValid = true;
            } catch (Exception e) {
            }
//                    e.printStackTrace();
        return xValue;

    }

    public void printBoard() {

    }


    // Simplified Printing
    private void print(String s) {
        System.out.print(s);
    }


}
