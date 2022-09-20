import java.util.Scanner;

public class Game {


    private boolean play;
    private int[] spacesUsed;
    private Scanner r;

    public static void main(String[] args) {
//        Board b = new Board();

        Game g = new Game();

//        Board b = new Board();
        g.play();
    }

    public Game() {
        play = true;

    }

    public void play() {

        r = new Scanner(System.in);
        int userint = 0;
        System.out.println("Hello. Welcome to Battleship! Let's get this work!");

        while (userint != 1 && userint != 2) {
            System.out.println("Would you like to play vs. a computer (1) or player (2)?");
            userint = r.nextInt();
        }

//        if(userint == 1)
        System.out.println("Player 1, press enter if you are ready!");
        r.nextLine();
        Player player1 = new Player();
        System.out.println("Player 2, press enter if you are ready!");
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

        while (play) {
            player1.attack(player2.getBoard());
            player2.attack(player1.getBoard());
            if (player1.getOtherPlayerSunken() == 5) {
                System.out.println("Game Over, player 2 wins, lets go, haha!");
                play = false;
            }
            if (player2.getOtherPlayerSunken() == 5) {
                System.out.println("Game Over, player 1 wins, lets go, haha!");
                play = false;
            }
        }


    }
}

