package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Tic-Tac-Toe game with AI */
public class TTTGraphic extends JPanel { // Turunkan TTTGraphic dari JPanel, bukan JFrame

    private static final int SIZE = 3; // Board size
    private static final int CELL_SIZE = 100; // Cell size in pixels
    private static final int WIDTH = SIZE * CELL_SIZE; // Window width
    private static final int HEIGHT = SIZE * CELL_SIZE; // Window height

    private Board board; // Logical board
    private Seed currentPlayer;
    private State currentState;
    private boolean isAIPlayerTurn;
    private AIPlayerMinimax aiPlayer;

    private JPanel gamePanel;
    private JPanel controlPanel;
    private JButton resetButton;

    public TTTGraphic() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));  // Menetapkan ukuran panel permainan
        setLayout(new BorderLayout()); // Gunakan BorderLayout untuk menempatkan kontrol dan game panel

        // Initialize game
        board = new Board();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        isAIPlayerTurn = false;

        // Initialize AI player
        aiPlayer = new AIPlayerMinimax(board);
        aiPlayer.setSeed(Seed.NOUGHT);

        // Setup game panel
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  // Panel permainan dengan ukuran tetap
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentState == State.PLAYING) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int row = mouseY / CELL_SIZE;
                    int col = mouseX / CELL_SIZE;

                    if (row >= 0 && row < SIZE && col >= 0 && col < SIZE
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        currentState = board.stepGame(currentPlayer, row, col);
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        gamePanel.repaint();

                        // Trigger AI move
                        if (currentState == State.PLAYING && currentPlayer == Seed.NOUGHT) {
                            aiMove();
                        }
                    }
                } else {
                    newGame();
                }
            }
        });

        // Setup control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Gunakan FlowLayout untuk kontrol panel
        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> newGame());
        controlPanel.add(new JLabel("Tic Tac Toe"));
        controlPanel.add(resetButton);

        // Add panels to this JPanel ( bukan JFrame )
        add(gamePanel, BorderLayout.CENTER);   // Panel permainan di tengah
        add(controlPanel, BorderLayout.NORTH); // Panel kontrol di atas
    }

    private void aiMove() {
        if (currentState == State.PLAYING) {
            int[] move = aiPlayer.move();
            if (move != null) {
                currentState = board.stepGame(Seed.NOUGHT, move[0], move[1]);
                currentPlayer = Seed.CROSS;
                gamePanel.repaint();
            }
        }
    }

    private void newGame() {
        board.newGame();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        gamePanel.repaint();
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));

        // Draw grid
        g2d.setColor(Color.BLACK);
        for (int i = 1; i < SIZE; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, HEIGHT);
            g2d.drawLine(0, i * CELL_SIZE, WIDTH, i * CELL_SIZE);
        }

        // Draw X and O
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int x1 = col * CELL_SIZE + 20;
                int y1 = row * CELL_SIZE + 20;
                int x2 = (col + 1) * CELL_SIZE - 20;
                int y2 = (row + 1) * CELL_SIZE - 20;

                if (board.cells[row][col].content == Seed.CROSS) {
                    g2d.setColor(Color.RED);
                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.drawLine(x2, y1, x1, y2);
                } else if (board.cells[row][col].content == Seed.NOUGHT) {
                    g2d.setColor(Color.BLUE);
                    g2d.drawOval(x1, y1, CELL_SIZE - 40, CELL_SIZE - 40);
                }
            }
        }

        // Display game state
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        if (currentState == State.CROSS_WON) {
            showGameOverMessage("Player X Wins!");
        } else if (currentState == State.NOUGHT_WON) {
            showGameOverMessage("Player O Wins!");
        } else if (currentState == State.DRAW) {
            showGameOverMessage("It's a Draw!");
        }
    }

    private void showGameOverMessage(String message) {
        // Tampilkan pesan game over terlebih dahulu
        JOptionPane.showMessageDialog(this, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Setelah pesan ditutup, mulai permainan baru
        newGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TTTGraphic().setVisible(true));
    }
}
