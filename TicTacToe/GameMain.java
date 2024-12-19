package TicTacToe;

import java.util.Scanner;

/**
 * The main class for the Tic-Tac-Toe (Console-OO, non-graphics version)
 * It acts as the overall controller of the game.
 */
public class GameMain {
    private Board board;               // Board permainan
    private State currentState;        // Status permainan saat ini
    private Seed currentPlayer;        // Pemain yang sedang bermain (X atau O)

    private static Scanner in = new Scanner(System.in);

    public GameMain() {
        initGame();   // Inisialisasi permainan
        newGame();    // Mulai permainan baru
        do {
            stepGame();  // Pemain melakukan langkah
            paintBoard();  // Menampilkan board
            if (currentState == State.CROSS_WON) {
                System.out.println("'X' won!\nBye!");
            } else if (currentState == State.NOUGHT_WON) {
                System.out.println("'O' won!\nBye!");
            } else if (currentState == State.DRAW) {
                System.out.println("It's a Draw!\nBye!");
            }
            // Ganti pemain
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        } while (currentState == State.PLAYING); // Teruskan permainan jika belum selesai
    }

    // Fungsi untuk inisialisasi game
    public void initGame() {
        board = new Board();   // Membuat board baru
    }

    // Fungsi untuk memulai permainan baru
    public void newGame() {
        board.newGame();  // Setel ulang board
        currentPlayer = Seed.CROSS;  // Pemain X yang pertama
        currentState = State.PLAYING;  // Status permainan
    }

    // Fungsi untuk menangani langkah pemain
    public void stepGame() {
        boolean validInput = false;  // Validasi input pemain
        do {
            String icon = currentPlayer.getDisplayName();
            System.out.print("Player '" + icon + "', enter your move (row[1-3] column[1-3]): ");
            int row = in.nextInt() - 1;  // Input baris (diubah ke 0-based)
            int col = in.nextInt() - 1;  // Input kolom (diubah ke 0-based)

            // Validasi langkah
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS && board.cells[row][col].content == Seed.NO_SEED) {
                currentState = board.stepGame(currentPlayer, row, col);
                validInput = true;  // Input valid
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1) + ") is not valid. Try again...");
            }
        } while (!validInput);  // Ulangi hingga input valid
    }

    // Fungsi untuk menampilkan board ke konsol
    public void paintBoard() {
        System.out.println();
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                System.out.print(board.cells[row][col].content.getDisplayName());
                if (col < Board.COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (row < Board.ROWS - 1) {
                for (int col = 0; col < Board.COLS; ++col) {
                    System.out.print("-");
                    if (col < Board.COLS - 1) System.out.print("+");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new GameMain();  // Menjalankan permainan
    }
}