import java.util.ArrayList;

public class Ship {

    // Takes in an x and y position and rotation and length
    // Uses the rotation to calculate the x and y coodinates that the ship occupies
    // It uses this info and stores it into a 2d array to be accessed by other classes in
    // a getPos() method


    //variables
    private int ships;
    private ArrayList<Integer> xpos;
    private ArrayList<Integer> ypos;
    private ArrayList<String> names;

    private boolean isPlayer;
    private int length;
    private boolean isVertical;
    private int[][] coordinates;
    private String name;

    // Each ship has its own unique letter used when printing it out
    private String letter;


    //the ship
    public Ship(int length, int x, int y, boolean isVertical, String letter) {

        // Inializing the Array List
        names = new ArrayList<String>() {{

            add("USS Lin");
            add("USS Justus");
            add("USS-R-askolnikov");
            add("The Rhino");
            add("The Black Pearl");
            add("Mayflower");
            add("Titanic");
            add("The Battle Bus");
            add("Bezos Superyacht");
            add("USSR Aquaholic");
            add("Row vs. Wade");
            add("The Crazy Oar-deal");
            add("The Bull ship");
            add("Tax Sea-vasion");
            add("Knot on my Watch");
            add("Unsinkable II");
            add("Usain Boat");
            add("The Codfather");
            add("Liquid Assest");


        }};
        int nameNum = ((int) (Math.random() * 19));
        name = names.get(nameNum);
        names.remove(nameNum);
        xpos = new ArrayList<Integer>();
        ypos = new ArrayList<Integer>();


        // Initializing class variables
        this.length = length;
        this.letter = letter;



        //makes the ships coordinates
        if (isVertical == true) {
            for (int i = 0; i < length; i++) {
                xpos.add(x);
                ypos.add(y + i);
            }
        } else {
            for (int i = 0; i < length; i++) {
                ypos.add(y);
                xpos.add(x + i);
            }
        }


        //makes the coordinates into an array
        coordinates = new int[length][2];

        for (int i = 0; i < length; i++) {
            coordinates[i][0] = xpos.get(i);
            coordinates[i][1] = ypos.get(i);
        }

    }


    //returns said array
    public int[][] getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }

    public String getLetter() {
        return letter;
    }


}
