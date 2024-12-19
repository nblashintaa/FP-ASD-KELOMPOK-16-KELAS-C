package ConnectFour;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input name for the Human player
        System.out.print("Enter name for Human player: ");
        String humanName = scanner.nextLine();

        // Set AI's name automatically to "Computer"
        String aiName = "Computer";

        // Create a new Board instance
        Board gameBoard = new Board();

        // Create a new ConnectFour game instance with the human player, AI player, and the board
        ConnectFour game = new ConnectFour(humanName, aiName, gameBoard);

        // Start the game
        game.playGame();
    }
}