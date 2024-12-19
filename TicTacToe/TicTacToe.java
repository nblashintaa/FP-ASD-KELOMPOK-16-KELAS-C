package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToe extends JPanel {
    private static final int GRID_SIZE = 3; // Ukuran papan 3x3
    private static final int CELL_SIZE = 120; // Ukuran setiap sel
    private static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
    private static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE + 40; // Tambahkan ruang untuk status
    private static final int GRID_WIDTH = 8; // Ketebalan garis grid
    private static final Color GRID_COLOR = Color.LIGHT_GRAY;
    public static final Color COLOR_CROSS = Color.RED; // Warna untuk "X"
    public static final Color COLOR_NOUGHT = Color.BLUE; // Warna untuk "O"

    private Seed currentPlayer; // Pemain saat ini (X atau O)
    private State currentState; // Status permainan
    private Seed[][] board; // Papan permainan
    private JLabel statusBar; // Status permainan

    public TicTacToe() {
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        board = new Seed[GRID_SIZE][GRID_SIZE];
        statusBar = new JLabel(" ");
        statusBar.setFont(new Font("Arial", Font.BOLD, 14));
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);

        // Inisialisasi papan
        newGame();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                if (currentState == State.PLAYING) {
                    int row = mouseY / CELL_SIZE;
                    int col = mouseX / CELL_SIZE;

                    if (row < GRID_SIZE && col < GRID_SIZE && board[row][col] == Seed.NO_SEED) {
                        board[row][col] = currentPlayer;
                        updateGame(currentPlayer, row, col);

                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        repaint();
                    }
                } else {
                    newGame();
                    repaint();
                }
            }
        });
    }

    private void newGame() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                board[row][col] = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        statusBar.setText("X's Turn");
    }

    private void updateGame(Seed seed, int row, int col) {
        if (hasWon(seed, row, col)) {
            currentState = (seed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
            statusBar.setText((seed == Seed.CROSS ? "X" : "O") + " Won! Click to play again.");
        } else if (isDraw()) {
            currentState = State.DRAW;
            statusBar.setText("It's a Draw! Click to play again.");
        }
    }

    private boolean hasWon(Seed seed, int row, int col) {
        return (board[row][0] == seed && board[row][1] == seed && board[row][2] == seed)
                || (board[0][col] == seed && board[1][col] == seed && board[2][col] == seed)
                || (row == col && board[0][0] == seed && board[1][1] == seed && board[2][2] == seed)
                || (row + col == 2 && board[0][2] == seed && board[1][1] == seed && board[2][0] == seed);
    }

    private boolean isDraw() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (board[row][col] == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(GRID_COLOR);
        for (int row = 1; row < GRID_SIZE; ++row) {
            g.fillRect(0, row * CELL_SIZE - GRID_WIDTH / 2, CANVAS_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GRID_SIZE; ++col) {
            g.fillRect(col * CELL_SIZE - GRID_WIDTH / 2, 0, GRID_WIDTH, CANVAS_HEIGHT);
        }

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                int x1 = col * CELL_SIZE + 20;
                int y1 = row * CELL_SIZE + 20;

                if (board[row][col] == Seed.CROSS) {
                    g.setColor(COLOR_CROSS);
                    g.drawLine(x1, y1, x1 + 80, y1 + 80);
                    g.drawLine(x1 + 80, y1, x1, y1 + 80);
                } else if (board[row][col] == Seed.NOUGHT) {
                    g.setColor(COLOR_NOUGHT);
                    g.drawOval(x1, y1, 80, 80);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.drawString(statusBar.getText(), 10, CANVAS_HEIGHT - 10);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        TicTacToe game = new TicTacToe();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.add(game.statusBar, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}