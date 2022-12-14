import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Written by Jack Justus
public class Board {

    // Class variables
    private String[][] squares;
    private ArrayList<Ship> ships;

    // Initialized in the initializer, same every game
    private int[] shipLengths;

    // Constants
    static final int NUM_SHIPS = 5;
    private final String OCEAN_HIT_SYMBOL = "^";
    private final String SHIP_HIT_SYMBOL = "x";
    private final String EMPTY_BOARD_SYMBOL = "_";
    Scanner s;

    // Initializing the Board object, takes in the numShips (constant set in game class)
    // Runs through a for loop initializing each ship with the player's input


    public Board() {

        // lengths of the ships
        shipLengths = new int[]{5, 4, 3, 3, 2};

        // Make scanner
        s = new Scanner(System.in);

        // Initialize arrayList
        ships = new ArrayList<>();

        // Initialize Board squares
        // -2 = ship hit
        // -1 = ocean hit
        // 0 = empty
        // 1 = ship
        squares = new String[10][10];

        // Making all spaces in the board empty by default
        for (String[] square : squares) Arrays.fill(square, "0");

        // Initializing Ship objects using player input
        print("Time to make your ships!\n\n");

        //
        boolean firstRun = true;


        for (int i = 0; i < NUM_SHIPS; i++) {


            // Input validation confirmation
            // Turns false when the input validation fails.
            boolean inputWorks = false;

            // User Clarity


            // QOL
            int shipLength = shipLengths[i];

            while (!inputWorks) {

                if (!firstRun) print("Sorry, your input is not valid. Try again!");


                // Starts true and gets sets false when it fails
                inputWorks = true;

                printBoard();

                // Telling the user how long their ship is
                print("    SHIP LENGTH: " + shipLength + "\n");


                // Finding the rotation of the ships before they choose the coordinates
                // This needs to be done for the simplicity of input validation
                int rotation = getRotationalValue();

                // This int rotation needs to be converted to a boolean so that the x input validation can use it
                boolean isVertical;
                if (rotation == 1) isVertical = true;
                else if (rotation == 2) isVertical = false;
                else {
                    // This signals that the input is not valid
                    inputWorks = false;
                    continue;
                }


                // We now use this method to retrieve the x value of the ship
                int x = getValue(shipLength, "x");


                // Time for y value
                int y = getValue(shipLength, "y");


                // Converting isVertical to a string
                String verticalString;
                if (isVertical) verticalString = "vertical";
                else verticalString = "horizontal";

                // At this point we can run input validation for all the inputs.
                if (inputValidation(x, y, isVertical, shipLength)) {


                    // Giving the player feedback based on all of their input
                    print("\nYou placed a " + verticalString + " length " + shipLength + " ship at " + x + ", " + y + "\n");


                    // Assigning a letter to the ship based on how many times this loop has run
                    // Also making sure the letter is valid
                    String shipLetter = Translate.convert(i);


                    // Actually making the ship object
                    // int length, int x, int y, boolean isVertical, String letter
                    ships.add(new Ship(shipLength, x, y, isVertical, shipLetter));

                    print("It is named " + ships.get(i).getName() + "\n");


                    // Marking on the board where the ship is placed
                    markBoardShipPlacement(ships.get(i));

//                    clearConsole();

                    // Differentiating between ship placements
                    print("Ship " + (i + 1) + " Placed.\n\n\n\n");

                } else {
                    // If input validation fails
                    inputWorks = false;

                    print("\n\nSorry, but that input is not valid. Please try again.\n\n");


                }
            }

        }
        printBoard();
        clearConsole();
    }


    public Board(boolean debugMode) {

        // This init is so we can test code without having to set up ships


        // lengths of the ships
        shipLengths = new int[]{5, 4, 3, 3, 2};

        // Make scanner
        s = new Scanner(System.in);

        // Initialize arrayList
        ships = new ArrayList<>();

        // Initialize Board squares
        // -1 = hit
        // 0 = empty
        // 1 = ship
        squares = new String[10][10];

        // Making all spaces in the board empty by default
        for (String[] square : squares) Arrays.fill(square, "0");


        // Actually making the ship object
        // int length, int x, int y, boolean isVertical, String letter
        ships.add(new Ship(5, 0, 0, true, "A"));
        ships.add(new Ship(4, 1, 0, true, "B"));
        ships.add(new Ship(3, 2, 0, true, "C"));
        ships.add(new Ship(3, 3, 0, true, "D"));
        ships.add(new Ship(2, 4, 0, true, "E"));

        // Marking on the board where the ship is placed
        for (Ship s : ships)
            markBoardShipPlacement(s);

    }

    public int getNumShips() {
        return NUM_SHIPS;
    }

    private void markBoardShipPlacement(Ship s) {
        // Marking on the board where the ship is placed for initial ship setup

        int[][] shipPositions = s.getCoordinates();

        for (int[] shipPosition : shipPositions) {

            // Extracting the coordinates from the array
            int x = shipPosition[0];
            int y = shipPosition[1];

            // Getting the ship's associated letter
            String letter = s.getLetter();

            // Setting the position of the ship on the board to the ship's letter
            // The y is vertical so its first
//            print(x + ", " + y + "\n");
            squares[y][x] = letter;

        }

    }


    private int getRotationalValue() {

        // Making the while loop work
        boolean inputValid = false;

        // Intellij is being stupid about initializing this variable to do this
        int rotationalValue = 0;

        while (!inputValid) try {
            print("\nWould you like your ship to be vertical or horizontal?\n");
            print("1 = Vertical        2 = Horizontal\n");
            rotationalValue = safeNextInt();

            // Mini input validation
            if (rotationalValue != 1 && rotationalValue != 2) rotationalValueNotValid(false);

            // Checking to see if the input is valid
            // If it is valid then the loop will end
            while (!inputRotationValidation(rotationalValue)) rotationalValue = safeNextInt();
            inputValid = true;

        } catch (Exception e) {
            rotationalValueNotValid(true);
        }
//                    e.printStackTrace();
        print("\n");
        return rotationalValue;


    }

    private void rotationalValueNotValid(boolean bugFixRequired) {

        print("Sorry, but that is not a valid input. Please enter either [1] or [2].\n");

        // This is sometimes required in order to patch a scanner bug
        if (bugFixRequired) safeNextLine();

    }

    private int getValue(int shipLength, String typeOfValue) {

        // typeOfValue can either be "x" or "y"

        // Intellij is being stupid about initializing this variable to do this
        int value = 0;

        // Master try catch because there is a lot of possible places of failure
        try {

            // What is printed and requested differs based on if it is an x or y value
            if (typeOfValue.equals("x")) {

                // This is always called first, so it prompts the player to what they are doing
                // As well as asking them the x value
                print("This ship has a length of " + shipLength + "\n");
                print("Please type the [x] value\n >> ");
                value = safeNextInt() - 1;

            } else if (typeOfValue.equals("y")) {

                print("Please type the [y] value\n >> ");
                // Because the player is inputting a letter we need to convert it to a number
                // Also scanner is being stupid so this is necessary
                String temp = safeNextLine();
                temp = safeNextLine();


                // I have to make sure the player inputted a letter
                while (Translate.convert(temp) == -1)
                    temp = safeNextLine();

                // For some reason a -1 is needed here
                value = Translate.convert(temp) - 1;
                // Putting this in a try catch just in case
//                try {
//                    value = Translate.convert(temp) - 1;
//
//                    // If the input the player entered was invalid
//                    while (value == -1) value = Translate.convert(temp) - 1;
//                } catch (Exception e) {
//                    // e.printStackTrace();
//
//                    print("The letter you entered is not valid");
//
//                    // This flags the input validation and will prompt the player to input a new number by running this method again
//                    return 999;
//                }

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


    private boolean inputValidation(int x, int y, boolean isVertical, int shipLength) {
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

//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
//        the phenomenon whereby a person is reluctant to abandon a strategy or course of action because they have invested heavily in it, even when it is clear that abandonment would be more beneficial.
//        "the sunk-cost fallacy creeps into a lot of major financial decisions"
        /*
        for (int i = 0; i < shipLength; i++) {
            for (Ship ship : ships) {
                for (int k = 0; k < ship.getCoordinates().length; k++) {
                    if (isVertical == true) {
                        if (x == ship.getCoordinates()[k][0]) {
                            if (y + i == ship.getCoordinates()[k][1]) {
                                print("fAILDED");
                                return false;
                            }
                        }
                    } else {
                        if (x + i == ship.getCoordinates()[k][0]) {
                            if (y == ship.getCoordinates()[k][1]) {
                                print("fAILDED");
                                return false;
                            }
                        }
                    }
                }
            }
        }

        print("works");
        return true;


         */


        // Start by creating a dummy ship object with an arraylist of the coordinates that it occupies
        // int length, int x, int y, boolean isVertical, String letter
        Ship s = new Ship(shipLength, x, y, isVertical, "Z");

        // Populating the arrayList with the coordinates
        ArrayList<int[]> shipCoords = new ArrayList<>(Arrays.asList(s.getCoordinates()));

        // Check if the x and y is within bounds
        if (x > 9 || y > 9) return false;

        // Check if the ships go off the board
        if (x > (10 - shipLength) && !isVertical) return false;
        if (y > (10 - shipLength) && isVertical) return false;


        for (int[] shipCoord : shipCoords) {
            // For each coordinate in the ship that we are placing,
            for (Ship ship : ships) {
                // We cross check it against the ships that have been placed's
                for (int k = 0; k < ship.getCoordinates().length; k++) {
                    // coordinates

                    // Peak Troubleshooting Techniques
//                    for (int[] z : shipCoords) {
//                        print("\n[");
//                        for (int g : z)
//                            print(g + " ,");
//                        print("]");
//                    }

                    // [0] is the x coordinate
                    if (shipCoord[0] == ship.getCoordinates()[k][0] && shipCoord[1] == ship.getCoordinates()[k][1]) {
                        // If the ship we are placing's x & y cord = one of the ship's x & y cord
                        // Then the validation failed because it is not a valid placement

                        //print("INPUT VALIDATION FAILED\n");
                        return false;
                    }
                }
            }
        }

        return true;
    }


    public void printBoard() {

        print("      YOUR BOARD\n");

        // Grid
        print("  1 2 3 4 5 6 7 8 9 10\n");


        for (int i = 0; i < squares.length; i++) {

            // Printing letters
//            if (i > 0)
            print(Translate.convert(i) + " ");

            // Printing actual grid spaces
            for (int j = 0; j < squares.length; j++) {

                // Translating the values into visual objects

                switch (squares[i][j]) {
                    case "0":
                        print(EMPTY_BOARD_SYMBOL + " ");
                        break;
                    case "-1":
                        print(OCEAN_HIT_SYMBOL + " ");
                        break;
                    case "-2":
                        print(SHIP_HIT_SYMBOL + " ");
                        break;
                    default:
                        print(squares[i][j] + " ");
                        break;
                }

            }
            print("\n");
        }


        // Board Key
        print(EMPTY_BOARD_SYMBOL + ": - Nothing\n");
        print(OCEAN_HIT_SYMBOL + ": - Ocean Hit\n");
        print(SHIP_HIT_SYMBOL + ": - Ship Hit\n");

        // Ships
        for (int i = 0; i < ships.size(); i++) {
            print(Translate.convert(i) + ": - " + ships.get(i).getName() + "\n");
        }
    }


    public void printBoard(boolean infoBoard) {

        if (!infoBoard) {
            printBoard();
        }


        print("     ENEMY BOARD\n");

        // Grid
        print("  1 2 3 4 5 6 7 8 9 10\n");


        for (int i = 0; i < squares.length; i++) {

            // Printing letters
            print(Translate.convert(i) + " ");

            // Printing actual grid spaces
            for (int j = 0; j < squares.length; j++) {

                // Readability
                switch (squares[i][j]) {
                    case "0", "1" -> print(EMPTY_BOARD_SYMBOL + " ");
                    case "-1" -> print(OCEAN_HIT_SYMBOL + " ");
                    case "-2" -> print(SHIP_HIT_SYMBOL + " ");
                    default -> print(EMPTY_BOARD_SYMBOL + " ");
                }


            }
            print("\n");
        }
//        print("\n");


    }


    public void shoot(int x, int y) {
        // -1 if the ocean was hit; -2 if a ship was hit

        // Here we need to differentiate between a ship being hit or the ocean and mark the board accordingly
        if (squares[x][y].equals("0")) squares[x][y] = "-1";
        else
            // This means a ship was hit, because you cannot shoot at the same place twice
            squares[x][y] = "-2";

    }


    //checks to see if a ship has been sunk
    public boolean checkSunk(int x, int y) {


        // Return true if a ship sunk and false if it didn't


        // First thing is to check if the place that the player fired at had a ship on it - and what that ship is

        // You can see my pain troubleshooting this method from the number of print statements

//        print("Running checkSunk at " + x + ", " + y);

        for (Ship s : ships) {
            for (int[] coords : s.getCoordinates()) {

//                print("Checking coordinate [" + coords[1] + ", " + coords[0] + "]");

                if (coords[1] == x && coords[0] == y) {

//                    print("Ship Found at coordinate [" + x + ", " + y + "]");

                    // This means that the coordinate the player choose had a ship at it
                    // We now need to check if any more of the ship is on the board
                    String shipLetter = s.getLetter();
                    boolean isOnBoard = false;

                    for (int i = 0; i < squares.length; i++)
                        for (int j = 0; j < squares[i].length; j++)
                            if (squares[i][j].equals(shipLetter)) {
                                isOnBoard = true;

                            }

                    // Now if isOnBoard is still false, we know that the ship was sunk
                    if (!isOnBoard) {
                        System.out.println("You sunk the " + s.getName());
                        System.out.println();
                        System.out.println("Press enter to continue");
                        safeNextLine();
                        return !isOnBoard;
                    }
                }
            }

        }
        return false;

//        //defines which ship was hit
//        int hitShip = 0;
//
//        //figures out what ship was hit
//        for (int i = 0; i < ships.size(); i++) {
//            for (int j = 0; j < ships.get(i).getCoordinates().length; j++) {
//
//                if (ships.get(i).getCoordinates()[j][0] == x) {
//                    if (ships.get(i).getCoordinates()[j][1] == y) {
//                        hitShip = i;
//                    }
//                }
//
//            }
//        }
//
//
//        //checks to see if said ship is completely sunk
//        int shipSlots = ships.get(hitShip).getCoordinates().length;
//        int count = 0;
//        for (int i = 0; i < shipSlots; i++) {
//            if (b1.getIntSquares()[ships.get(hitShip).getCoordinates()[i][0]][ships.get(hitShip).getCoordinates()[i][1]].equals("-2")) {
//                count++;
//            }
//        }
//
//
//        if (count == shipSlots) {
//            System.out.println("You sunk the " + ships.get(hitShip).getName() + "!");
//            return true;
//        } else {
//            return false;
//        }


    }


    public int[][] getIntSquares() {
        // Returns the board as a 2d int array
        //1 = ship at coordinate, -1 hit ship at coordinate, 0 empty water at coordinate (If you are reading this DLIN i love u)

        int[][] intSquares = new int[squares.length][squares[0].length];


        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                // This runs for every slot in the board

                String slot = squares[i][j];

                switch (slot) {
                    case "A", "B", "C", "D", "E" ->
                        // There is a ship at the slot if this is true, so we make this spot 1
                            intSquares[i][j] = 1;
                    case "-1" -> intSquares[i][j] = -1;
                    case "-2" -> intSquares[i][j] = -2;
                    case "0", "_" -> intSquares[i][j] = 0;
                    default -> print("\n\nERROR IN getIntSquares(). PLEASE CHECK AND UPDATE THE METHOD");
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

    public void clearConsole() {
        for (int i = 0; i < 40; i++)
            print("\n");
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


                if (text != -999) validInput = true;
            } catch (Exception e) {
                print("\nSorry, but that input is not valid. Please try again\n");
                s.nextLine();
            }

        }

        return text;


    }


}
