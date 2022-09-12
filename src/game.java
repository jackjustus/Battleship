public class game {
    private boolean gOver;
    private int [] spacesUsed;

    public static void main(String[] args) {

        //Testing the board class
        Board b = new Board();
        r = new Scanner(System.in);
        userint = 0;
        System.out.println(">>Hello. Welcome to Battleship! Let's get this work!");

        while (userint != 1 && userint != 2) {
        System.out.println(">>Would you like to play vs. a computer (1) or player (2)?");
        userint = r.nextInt();
        }

//        if(userint == 1)
            System.out.println(">>Player 1, press enter if you are ready!");
            r.nextLine();
            Player player1 = new Player();
            System.out.println(">>Player 2, press enter if you are ready!");
            r.nextLine();
            Player player2 = new Player();

//        }
        //What we would do if we added a computer player
//        else {
//            System.out.println(">>Player 1, press enter if you are ready!");
//            r.nextLine();
//            Player player1 = new Player();
//            Player player2 = new Player("Computer");
//        }

        while(play){
            player1.attack(player2.getBoard());
        }




    }
}

