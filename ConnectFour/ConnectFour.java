package ConnectFour;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConnectFour {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int AI_PLAYER = 1;
    public static final int HUMAN_PLAYER = 2;
    public static final int EMPTY = 0;
    public static final int MAX_DEPTH = 5;

    private int[][] board;
    private Player humanPlayer;
    private Player aiPlayer;

    // Constructor
    public ConnectFour() {
        this.board = new int[ROWS][COLS];
    }

    public ConnectFour(String humanName, String aiName) {
        this.board = new int[ROWS][COLS];
        this.humanPlayer = new Player(humanName);
        this.aiPlayer = new Player(aiName);
    }

    public int[][] getBoard() {
        return board;
    }

    // Method to play the game
    public void playGame(String humanName, String aiName) {
        this.humanPlayer = new Player(humanName);
        this.aiPlayer = new Player(aiName);

        boolean isAITurn = false;

        while (true) {
            printBoard();

            if (isWinningMove(board, HUMAN_PLAYER)) {
                System.out.println(humanPlayer.getName() + " wins!");
                humanPlayer.addScore(1);
                break;
            } else if (isWinningMove(board, AI_PLAYER)) {
                System.out.println(aiPlayer.getName() + " wins!");
                aiPlayer.addScore(1);
                break;
            } else if (isDraw()) {
                System.out.println("It's a draw!");
                break;
            }

            if (isAITurn) {
                int bestMove = getBestMove();
                makeMove(bestMove, AI_PLAYER);
                System.out.println(aiPlayer.getName() + " chooses column: " + bestMove);
            } else {
                int col;
                do {
                    System.out.print(humanPlayer.getName() + ", enter your move (0-6): ");
                    col = new Scanner(System.in).nextInt();
                } while (!isValidMove(col));
                makeMove(col, HUMAN_PLAYER);
            }

            isAITurn = !isAITurn;
        }

        System.out.println("Scoreboard:");
        System.out.println(humanPlayer.getName() + ": " + humanPlayer.getScore());
        System.out.println(aiPlayer.getName() + ": " + aiPlayer.getScore());
    }

    private void printBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isDraw() {
        for (int col = 0; col < COLS; col++) {
            if (getBoard()[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    public void makeMove(int col, int player) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == EMPTY) {
                board[row][col] = player;
                break;
            }
        }
    }

    public boolean isValidMove(int col) {
        return col >= 0 && col < COLS && board[0][col] == EMPTY;
    }

    private List<Integer> validMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int col = 0; col < COLS; col++) {
            if (isValidMove(col)) {
                moves.add(col);
            }
        }
        return moves;
    }

    private int[][] simulateMove(int[][] board, int col, int player) {
        int[][] newBoard = new int[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(board[row], 0, newBoard[row], 0, COLS);
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (newBoard[row][col] == EMPTY) {
                newBoard[row][col] = player;
                break;
            }
        }
        return newBoard;
    }

    public int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int col : validMoves()) {
            int[][] newBoard = simulateMove(board, col, AI_PLAYER);
            int score = minimax(newBoard, MAX_DEPTH, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (score > bestScore) {
                bestScore = score;
                bestMove = col;
            }
        }
        return bestMove;
    }

    private int minimax(int[][] board, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        if (isWinningMove(board, AI_PLAYER)) return 1000;
        if (isWinningMove(board, HUMAN_PLAYER)) return -1000;
        if (isDraw() || depth == 0) return evaluateBoard(board);

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (int col : validMoves()) {
                int[][] newBoard = simulateMove(board, col, AI_PLAYER);
                int eval = minimax(newBoard, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col : validMoves()) {
                int[][] newBoard = simulateMove(board, col, HUMAN_PLAYER);
                int eval = minimax(newBoard, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    public boolean isWinningMove(int[][] board, int player) {
        // Check horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                        board[row][col + 1] == player &&
                        board[row][col + 2] == player &&
                        board[row][col + 3] == player) {
                    return true;
                }
            }
        }

        // Check vertical
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col] == player &&
                        board[row + 2][col] == player &&
                        board[row + 3][col] == player) {
                    return true;
                }
            }
        }

        // Check diagonal (positive slope)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                        board[row + 1][col + 1] == player &&
                        board[row + 2][col + 2] == player &&
                        board[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }

        // Check diagonal (negative slope)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                if (board[row][col] == player &&
                        board[row - 1][col + 1] == player &&
                        board[row - 2][col + 2] == player &&
                        board[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }

        return false;
    }

    private int evaluateBoard(int[][] board) {
        int score = 0;

        for (int row = 0; row < ROWS; row++) {
            if (board[row][COLS / 2] == AI_PLAYER) {
                score += 3;
            } else if (board[row][COLS / 2] == HUMAN_PLAYER) {
                score -= 3;
            }
        }

        score += evaluateLines(board, AI_PLAYER);
        score -= evaluateLines(board, HUMAN_PLAYER);

        return score;
    }

    private int evaluateLines(int[][] board, int player) {
        int score = 0;

        // Evaluate rows
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluateWindow(board[row][col], board[row][col + 1], board[row][col + 2], board[row][col + 3], player);
            }
        }

        // Evaluate columns
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                score += evaluateWindow(board[row][col], board[row + 1][col], board[row + 2][col], board[row + 3][col], player);
            }
        }

        // Evaluate diagonals (positive slope)
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluateWindow(board[row][col], board[row + 1][col + 1], board[row + 2][col + 2], board[row + 3][col + 3], player);
            }
        }

        // Evaluate diagonals (negative slope)
        for (int row = 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                score += evaluateWindow(board[row][col], board[row - 1][col + 1], board[row - 2][col + 2], board[row - 3][col + 3], player);
            }
        }

        return score;
    }

    private int evaluateWindow(int slot1, int slot2, int slot3, int slot4, int player) {
        int score = 0;
        int opponent = (player == AI_PLAYER) ? HUMAN_PLAYER : AI_PLAYER;

        int countPlayer = 0;
        int countOpponent = 0;
        int countEmpty = 0;

        int[] slots = {slot1, slot2, slot3, slot4};
        for (int slot : slots) {
            if (slot == player) countPlayer++;
            else if (slot == opponent) countOpponent++;
            else countEmpty++;
        }

        if (countPlayer == 4) score += 100;
        else if (countPlayer == 3 && countEmpty == 1) score += 5;
        else if (countPlayer == 2 && countEmpty == 2) score += 2;

        if (countOpponent == 4) score -= 100;
        else if (countOpponent == 3 && countEmpty == 1) score -= 4;
        else if (countOpponent == 2 && countEmpty == 2) score -= 1;

        return score;
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to Connect Four Game!\nChoose your difficulty level:");

        // Difficulty selection
        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        String selectedDifficulty = (String) JOptionPane.showInputDialog(null, "Select Difficulty",
                "Difficulty Level", JOptionPane.QUESTION_MESSAGE, null, difficultyLevels, difficultyLevels[1]);

        String humanName = JOptionPane.showInputDialog("Enter your name:");
        String aiName = "AI Player"; // AI is controlled by the system

        ConnectFour game = new ConnectFour(humanName, aiName);
        game.playGame(humanName, aiName);
    }
}
