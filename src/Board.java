import java.util.ArrayList;
import java.util.Scanner;

// Written by Jack Justus
public class Board {


    // Class variables
    private String[][] squares;
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
        squares = new String[10][10];

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


            // Giving the player feedback based on all of their input
            print("You placed a " + verticalString + " length " + shipLengths[i] + " ship at " + x + ", " + y + "\n");

            // Assigning a letter to the ship based on how many times this loop has ran
            String shipLetter = Translate.convert(i);


            // Actually making the ship object
            // int length, int x, int y, boolean isVertical, String letter
            ships.add(new Ship(shipLengths[i], x, y, isVertical, shipLetter));


            // Marking on the board where the ship is placed
            markBoardInitial(ships.get(i));

            // Ship(int length, int x, int y, boolean isVertical)
            //ships.get(i) = new Ship();

        }

    }

    private void markBoardInitial(Ship s) {
        // Marking on the board where the ship is placed

        int[][] shipPositions = s.getCoordinates();

        for (int i = 0; i < shipPositions.length; i++) {

            // Extracting the coordinates from the array
            int x = shipPositions[i][0];
            int y = shipPositions[i][1];

            // Getting the ship's associated letter
            String letter = s.getLetter();

            // Setting the position of the ship on the board to the ship's letter
            squares[x][y] = letter;

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

        // typeOfValue can either be "x" or "y"

        // Intellij is being stupid about initializing this variable to do this
        int value = 0;

        // Master try catch because there is a lot of possible places of failure
        try {

            // Printing the board to give the player a point of reference
            printBoard();

            // What is printed and requested differs based on if it is a x or y value
            if (typeOfValue.equals("x")) {

                // This is always called first, so it prompts the player to what they are doing
                // As well as asking them the x value
                print("This ship has a length of " + shipLengths[i] + ".\n");
                print("Where would you like to place it?\n Start by entering the x value.\n");
                print("Please type the x value in a number from 1 - 10.\n");
                value = s.nextInt();

            } else if (typeOfValue.equals("y")) {

                print("Awesome, thanks for the X value.\n Time for the Y.\n");
                print("Please type the Y value in a letter from A - J\n");
                // Because the player is inputting a letter we need to convert it to a number
                // Also scanner is being stupid so this is necessary
                String temp = s.nextLine();
                temp = s.nextLine();

                // Putting this in a try catch just in case
                try {
                    value = Translate.convert(temp);
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


    private boolean inputRotationValidation(int input) {

        if (input == 1 || input == 2)
            return true;
        return false;

    }


    private boolean inputValidation(boolean isVertical, int value, boolean isXValue, int shipLength) {

        //this bit checks to see if it doens't work
        int works = 0;


        //i do not know how this works or if it works
        //The Himalayas of Validation
        /*

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
                if (value > (10 - shipLength)){
                    return false;
                }
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
                if (value > (10 - shipLength)) {
                    return false;
                }
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
         */

        //the better validation
        for (int i = 0; i < shipLength; i++) {

            for (int j = 0; j < ships.size(); j++) {

                for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {


                    if (isXValue == true) {
                        if (isVertical == true) {
                            if (value == ships.get(j).getCoordinates()[1][k]) {
                                works++;
                            }
                        } else {
                            if (value > (10 - shipLength)) {
                                return false;
                            }
                            if (value == ships.get(j).getCoordinates()[0][k]) {
                                works++;
                            }
                        }

                    } else {
                        if (isVertical == true) {
                            if (value > (10 - shipLength)) {
                                return false;
                            }
                            if (value == ships.get(j).getCoordinates()[k][1]) {
                                works++;
                            }
                        } else {
                            if (value == ships.get(j).getCoordinates()[k][0]) {
                                works++;
                            }
                        }
                    }
                }
            }
        }


        //more checking to see if it works
        if (works == shipLength) {
            return false;
        } else {
            return true;
        }


    }


    public void printBoard() {
        //prints the board


        //prints outnumbrs
        print("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < squares.length; i++) {
            if (i > 0) {
                print(Translate.convert(i));
            }
            for (int j = 0; j < squares.length; j++) {
                print(squares[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void shoot(int x, int y) {
        squares[x][y] = "-1";
    }


    //checks to see if a ship has been sunk
    public boolean checkSunk(int x, int y, Board b1) {

        //defines which ship was hit
        int hitShip = 0;


        //figures out what ship was hit
        for (int i = 0; i < ships.size(); i++) {
            for (int j = 0; j < ships.get(i).getCoordinates().length; j++) {

                if (ships.get(i).getCoordinates()[j][0] == x) {
                    if (ships.get(i).getCoordinates()[j][1] == y) {
                        hitShip = i;
                    }
                }

            }
        }


        //checks to see if said ship is completely sunk
        int shipSlots = ships.get(hitShip).getCoordinates().length;
        int count = 0;
        for (int i = 0; i < shipSlots; i++) {
            if (b1.getIntSquares()[ships.get(hitShip).getCoordinates()[i][0]][ships.get(hitShip).getCoordinates()[i][1]] == -1) {
                count++;
            }
        }


        if (count == shipSlots) {
            System.out.println("You sunk the " + ships.get(hitShip).getName() + "!");
            return true;
        } else {
            return false;
        }

    }


    public int[][] getIntSquares() {

        int[][] intSquares = new int[squares.length][squares[0].length];
        // Converting the array to an array of Ints
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[i].length; i++) {

                // This statement will fail if the space is not a number
                try {
                    intSquares[i][j] = Integer.parseInt(squares[i][j]);
                } catch (Exception e) {

                }

            }
        }
        return squares;
    }


    // Simplified Printing
    private void print(String s) {
        System.out.print(s);
    }

    private void print(int i) {
        System.out.print(i);
    }


}
