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

        // Initialize Board squares
        // -1 = hit
        // 0 = empty
        // 1 = ship
        squares = new int[10][10];

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


            // We now use this method to retrieve the x value of the ship
            int x = getValue(i, "x");
            // And then validate the input
            // inputXValidation(boolean isVertical, int value, boolean isXValue, int shipLength)
            while (!inputValidation(isVertical, x, true, shipLengths[i]))
                x = getValue(i, "x");

            // Time for y value

            int y = getValue(i, "y");

            // Input Validation
            while (!inputValidation(isVertical, y, false, shipLengths[i])) {

                print("Sorry, but that coordinate is not valid.\nPlease try another y value");
                y = getValue(i, "y");

            }

            // Converting isVertical to a string
            String verticalString;
            if (isVertical)
                verticalString = "vertical";
            else
                verticalString = "horizontal";

            print("You placed a " + verticalString + " length " + shipLengths[i] + " ship at " + x + ", " + y + "\n");


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
                while (!inputRotationValidation(rotationalValue))
                    rotationalValue = s.nextInt();
                inputValid = true;

            } catch (Exception e) {
            }
//                    e.printStackTrace();
        return rotationalValue;


    }

    private int getValue(int i, String typeOfValue) {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int value = 0;

        // Master try catch because there is a lot of possible places of failure
        try {
            print("This ship has a length of " + shipLengths[i] + ".\n");
            print("Where would you like to place it?\n");
            print("Please type the " + typeOfValue + " position in a ");

            // What the player types differs based on if it is a x or y value
            if (typeOfValue.equals("x")) {

                print("number from 1 - 10.\n");
                value = s.nextInt();

            } else if (typeOfValue.equals("y")) {

                print("letter from A - J\n");
                // Because the player is inputting a letter we need to convert it to a number
                // Also scanner is being stupid so this is necessary
                String temp = s.nextLine();
                temp = s.nextLine();

                // Putting this in a try catch just in case
                try {
                    value = translate.convert(temp);
                } catch (Exception e) {
                    // e.printStackTrace();

                    print("The letter you entered is not valid");

                    // This flags the input validation and will prompt the player to input a new number by running this method again
                    return 999;
                }

            }


        } catch (Exception e) {

            e.printStackTrace();
            print("getValue has failed");
        }
//                    e.printStackTrace();
        return value;

    }

    private int getYValue(int i) {

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
