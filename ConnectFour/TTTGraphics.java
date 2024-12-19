package ConnectFour;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Tic-Tac-Toe: Two-player Graphics version with Simple-OO in one class
 */ // Diubah ke permainan Connect Four
public class TTTGraphics extends JFrame {
    private static final long serialVersionUID = 1L;

    // Ukuran papan Connect Four
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int CELL_SIZE = 100;
    public static final int GRID_WIDTH = 5;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final int BOARD_WIDTH = CELL_SIZE * COLS;
    public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;

    // Warna dan font
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_GRID = Color.LIGHT_GRAY;
    public static final Color COLOR_PLAYER1 = new Color(255, 0, 0);  // Merah
    public static final Color COLOR_PLAYER2 = new Color(0, 0, 255);  // Biru
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Status game
    private Seed[][] board;
    private Seed currentPlayer;
    private State currentState;
    private JLabel statusBar;

    // Enumerasi untuk pemain dan status permainan
    public enum Seed {
        PLAYER1, PLAYER2, EMPTY
    }

    public enum State {
        PLAYING, DRAW, PLAYER1_WON, PLAYER2_WON
    }

    // Panel untuk menggambar papan
    private GamePanel gamePanel;

    public TTTGraphics() {
        board = new Seed[ROWS][COLS];
        gamePanel = new GamePanel();

        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;

                if (currentState == State.PLAYING) {
                    for (int row = ROWS - 1; row >= 0; row--) {
                        if (board[row][col] == Seed.EMPTY) {
                            board[row][col] = currentPlayer;
                            currentState = checkGameState(row, col);
                            currentPlayer = (currentPlayer == Seed.PLAYER1) ? Seed.PLAYER2 : Seed.PLAYER1;
                            break;
                        }
                    }
                } else {
                    resetGame();
                }

                repaint();
            }
        });

        statusBar = new JLabel("Player 1's Turn (Red)");
        statusBar.setFont(FONT_STATUS);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(gamePanel, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Connect Four");
        setVisible(true);

        resetGame();
    }

    private void resetGame() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = Seed.EMPTY;
            }
        }
        currentPlayer = Seed.PLAYER1;
        currentState = State.PLAYING;
        statusBar.setText("Player 1's Turn (Red)");
    }

    private State checkGameState(int row, int col) {
        Seed player = board[row][col];

        // Cek horizontal
        int count = 0;
        for (int c = 0; c < COLS; c++) {
            count = (board[row][c] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }

        // Cek vertikal
        count = 0;
        for (int r = 0; r < ROWS; r++) {
            count = (board[r][col] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }

        // Cek diagonal (slope positif)
        count = 0;
        for (int r = row, c = col; r >= 0 && c >= 0; r--, c--) {
            count = (board[r][c] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }
        for (int r = row + 1, c = col + 1; r < ROWS && c < COLS; r++, c++) {
            count = (board[r][c] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }

        // Cek diagonal (slope negatif)
        count = 0;
        for (int r = row, c = col; r < ROWS && c >= 0; r++, c--) {
            count = (board[r][c] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }
        for (int r = row - 1, c = col + 1; r >= 0 && c < COLS; r--, c++) {
            count = (board[r][c] == player) ? count + 1 : 0;
            if (count >= 4) return (player == Seed.PLAYER1) ? State.PLAYER1_WON : State.PLAYER2_WON;
        }

        // Cek DRAW
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == Seed.EMPTY) return State.PLAYING;
            }
        }

        return State.DRAW;
    }

    class GamePanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(COLOR_BG);

            // Gambar grid
            g.setColor(COLOR_GRID);
            for (int row = 1; row < ROWS; row++) {
                g.fillRect(0, row * CELL_SIZE - GRID_WIDTH_HALF, BOARD_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; col++) {
                g.fillRect(col * CELL_SIZE - GRID_WIDTH_HALF, 0, GRID_WIDTH, BOARD_HEIGHT);
            }

            // Gambar kepingan
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (board[row][col] != Seed.EMPTY) {
                        g.setColor((board[row][col] == Seed.PLAYER1) ? COLOR_PLAYER1 : COLOR_PLAYER2);
                        g.fillOval(col * CELL_SIZE + 10, row * CELL_SIZE + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                    }
                }
            }

            // Tampilkan status
            if (currentState == State.PLAYER1_WON) {
                statusBar.setText("Player 1 (Red) Won! Click to restart.");
            } else if (currentState == State.PLAYER2_WON) {
                statusBar.setText("Player 2 (Blue) Won! Click to restart.");
            } else if (currentState == State.DRAW) {
                statusBar.setText("It's a Draw! Click to restart.");
            } else {
                statusBar.setText((currentPlayer == Seed.PLAYER1) ? "Player 1's Turn (Red)" : "Player 2's Turn (Blue)");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TTTGraphics());
    }
}
