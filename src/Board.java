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

        // Making all spaces in the board empty by default
        for (int i = 0; i < squares.length; i++)
            for (int j = 0; j < squares[i].length; j++)
                squares[i][j] = "0";

        // Initializing Ship objects using player input
        print("Time to make your ships!\n\n");
        for (int i = 0; i < NUM_SHIPS; i++) {

            // Input validation confirmation
            // Turns false when the input validation fails.
            boolean inputWorks = false;

            boolean firstRun = true;

            while (!inputWorks) {

                if (!firstRun)
                    print("Sorry, your input is not valid. Try again!");


                // Starts true and gets sets false when it fails
                inputWorks = true;


                // Finding the rotation of the ships before they choose the coordinates
                // This needs to be done for the simplicity of input validation
                int rotation = getRotationalValue();

                // This int rotation needs to be converted to a boolean so that the x input validation can use it
                boolean isVertical;
                if (rotation == 1)
                    isVertical = true;
                else if (rotation == 2)
                    isVertical = false;
                else
                    // This goes to the top of the while loop because they did not put a valid input
                    continue;


                // We now use this method to retrieve the x value of the ship
                int x = getValue(i, "x");


                // Time for y value
                int y = getValue(i, "y");


                // Converting isVertical to a string
                String verticalString;
                if (isVertical)
                    verticalString = "vertical";
                else
                    verticalString = "horizontal";

                // At this point we can run input validation for all the inputs.
                //TODO: Make Input Validation


                // Giving the player feedback based on all of their input
                print("You placed a " + verticalString + " length " + shipLengths[i] + " ship at " + x + ", " + y + "\n");


                // Assigning a letter to the ship based on how many times this loop has run
                String shipLetter = Translate.convert(i);


                // Actually making the ship object
                // int length, int x, int y, boolean isVertical, String letter
                ships.add(new Ship(shipLengths[i], x - 1, y - 1, isVertical, shipLetter));


                // Marking on the board where the ship is placed
                markBoardShipPlacement(ships.get(i));


                printBoard();

                // Differentiating between ship placements
                print("\n\nSHIP PLACEMENT COMPLETE\n\n");

            }

        }
    }

    private void markBoardShipPlacement(Ship s) {
        // Marking on the board where the ship is placed

        int[][] shipPositions = s.getCoordinates();

        for (int i = 0; i < shipPositions.length; i++) {

            // Extracting the coordinates from the array
            int x = shipPositions[i][0];
            int y = shipPositions[i][1];

            // Getting the ship's associated letter
            String letter = s.getLetter();

            // Setting the position of the ship on the board to the ship's letter
            // The y is vertical so its first
            squares[y][x] = letter;

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
            if (typeOfValue.equals("x"))
                printBoard();

            // What is printed and requested differs based on if it is an x or y value
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
        return input == 1 || input == 2;
    }


    private boolean inputValidation(boolean isVertical, int value, boolean isXValue, int shipLength) {
//
//        //this bit checks to see if it doens't work
//        int works = 0;
//
//
//        //i do not know how this works or if it works
//        //The Himalayas of Validation
//        /*
//
//        if (isXValue == true) {
//            if (isVertical == true) {
//                for (int i = 0; i < shipLength; i++) {
//
//                    for (int j = 0; j < ships.size(); j++) {
//
//                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {
//
//                            if (value == ships.get(j).getCoordinates()[1][k]) {
//                                works++;
//                            }
//                        }
//                    }
//                }
//            } else {
//                if (value > (10 - shipLength)){
//                    return false;
//                }
//                    for (int i = 0; i < shipLength; i++) {
//
//                        for (int j = 0; j < ships.size(); j++) {
//
//                            for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {
//
//                                if (value == ships.get(j).getCoordinates()[0][k]) {
//                                    works++;
//                                }
//                            }
//                        }
//                    }
//            }
//        } else {
//            if (isVertical == true) {
//                if (value > (10 - shipLength)) {
//                    return false;
//                }
//                for (int i = 0; i < shipLength; i++) {
//
//                    for (int j = 0; j < ships.size(); j++) {
//
//                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {
//
//                            if (value == ships.get(j).getCoordinates()[k][1]) {
//                                works++;
//                            }
//                        }
//                    }
//                }
//            } else {
//                for (int i = 0; i < shipLength; i++) {
//
//                    for (int j = 0; j < ships.size(); j++) {
//
//                        for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {
//
//                            if (value == ships.get(j).getCoordinates()[k][0]) {
//                                works++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//         */
//
//        //the better validation
//
//        for (int i = 0; i < shipLength; i++) {
//
//            // chekc show meny ships we have
//            for (int j = 0; j < ships.size(); j++) {
//
//
//                //checks lengh of the ship
//                for (int k = 0; k < ships.get(j).getCoordinates().length; k++) {
//
//
//                    //if were chicking a x value we do this
//                    if (isXValue == true) {
//
//                        //now if thre hsip is vertical we do this
//                        if (isVertical == true) {
//
//                            //if the x calue is the same as any of the x values in a ship we move on
//                            if (value == ships.get(j).getCoordinates()[1][k]) {
//                                works++;
//                            }
//
//                            //if were checking y value we do this
//                        } else {
//
//                            //if the sip is ges off the edge it returns fales
//                            if (value > (10 - shipLength)) {
//                                return false;
//                            }
//
//                            //now if it doesnt go of the edge
//                            //if the x value is the same as any of the x values we move on
//                            if (value == ships.get(j).getCoordinates()[0][k]) {
//                                works++;
//                            }
//                        }
//
//
//
//                        //we do this if the value is for a y
//                    } else {
//                        if (isVertical == true) {
//
//                            //chekc if go off bored
//                            if (value > (10 - shipLength)) {
//                                return false;
//                            }
//
//                            //if y value is same as ships goin verticly we move on
//                            if (value == ships.get(j).getCoordinates()[k][1]) {
//                                works++;
//                            }
//
//                            //if y value is same as ships going horizontally we move on
//                        } else {
//                            if (value == ships.get(j).getCoordinates()[k][0]) {
//                                works++;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//
//
//        //if the works counter is the same as the ships length then it doesnt work
//        //if it is not then it does work
//
//
//        //more checking to see if it works
//        if (works == shipLength) {
//            return false;
//        } else {
//            return true;
//        }

        return true;
    }


    public void printBoard() {
        //prints the board

        // Grid
        print("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < squares.length; i++) {

            // Printing letters
//            if (i > 0)
            print(Translate.convert(i) + " ");

            // Printing actual grid spaces
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
                    // This will run if there is a ship at the specified area.
                    intSquares[i][j] = 1;
                }

            }
        }
        return intSquares;
    }


    // Simplified Printing
    private void print(String s) {
        System.out.print(s);
    }

    private void print(int i) {
        System.out.print(i);
    }


}
