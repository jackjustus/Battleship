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

        // Initialize arrayList
        ships = new ArrayList<Ship>();

        // Initializing Ship objects using player input
        print("Time to make your ships!\n\n");
        for (int i = 0; i < NUM_SHIPS; i++) {

            // Finding the rotation of the ships before they choose the coordinates
            // This needs to be done for the simplicity of input validation
            int rotation = getRotationalValue();

            // This insures that the rotational input is valid
            while (!inputRotationValidation(rotation))
                rotation = getRotationalValue();

            // This int rotation needs to be converted to a boolean so that the x input validation can use it
            boolean isVertical;
            if (rotation == 1)
                isVertical = true;
            else
                isVertical = false;


            // We now use this method to retrive the x value of the ship
            int x = getXValue(i);
            // And then validate the input
            // inputXValidation(boolean isVertical, int value, boolean isXValue, int shipLength)
            while (!inputXValidation(isVertical, x, true, shipLengths[i]))
                x = getXValue(i);


            // Ship(int length, int x, int y, boolean isVertical)
            //ships.get(i) = new Ship();

        }

    }

    private int getRotationalValue() {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int rotationalValue = 0;

        while (!inputValid)
            try {
                print("Would you like your ship to be horizontal or vertical?\n");
                print("1 = Vertical        2 = Horizontal\n");
                rotationalValue = s.nextInt();

                // Checking to see if the input is valid
                // If it is valid then the loop will end
                if (inputRotationValidation(rotationalValue))
                    inputValid = true;

            } catch (Exception e) {
            }
//                    e.printStackTrace();
        return rotationalValue;


    }

    private int getXValue(int i) {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int xValue = 0;

        while (!inputValid)
            try {
                print("This ship has a length of " + shipLengths[i] + ".\n");
                print("Where would you like to place it?\n");
                print("Please type the x position in a single digit integer from 1-10\n");
                xValue = s.nextInt();

                // Input validation is done separately
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
                yValue = translate.convert(tempYValue);


            } catch (Exception e) {
            }
//                    e.printStackTrace();
        return yValue;

    }

    private boolean inputRotationValidation(int input) {

        if (input == 1 || input == 2)
            return true;
        return false;

    }

    // Kaden's Code
    private boolean inputXValidation(boolean isVertical, int value, boolean isXValue, int shipLength) {


        //i do not know how this works or if it works
        int works = 0;
        if (isXValue == true) {
            if (isVertical == true) {
                for (int i = 0; i < shipLength; i++) {

                    for (int j = 0; j < ships.size(); j++) {

                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {

                            if (value == ships.get(j).getCoordinates()[1][k]) {
                                works++;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < shipLength; i++) {

                    for (int j = 0; j < ships.size(); j++) {

                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {

                            if (value == ships.get(j).getCoordinates()[0][k]) {
                                works++;
                            }
                        }
                    }
                }
            }


        } else {


            if (isVertical == true) {
                for (int i = 0; i < shipLength; i++) {

                    for (int j = 0; j < ships.size(); j++) {

                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {

                            if (value == ships.get(j).getCoordinates()[k][1]) {
                                works++;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < shipLength; i++) {

                    for (int j = 0; j < ships.size(); j++) {

                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {

                            if (value == ships.get(j).getCoordinates()[k][0]) {
                                works++;
                            }
                        }
                    }
                }
            }


        }


        if (works == shipLength) {
            return false;
        } else {
            return true;
        }
        // Take the value and orentation and determine if the ship's position is wihtin the bounds of the board
        // Return the

    }


    public void printBoard() {

    }


    // Simplified Printing
    private void print(String s) {
        System.out.print(s);
    }


}
