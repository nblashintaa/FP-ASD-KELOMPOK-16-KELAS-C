package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** Tic-Tac-Toe game with AI */
public class TTTGraphic extends JPanel {

    private static final int SIZE = 3; // Board size
    private static final int CELL_SIZE = 100; // Cell size in pixels
    private static final int WIDTH = SIZE * CELL_SIZE; // Window width
    private static final int HEIGHT = SIZE * CELL_SIZE + 30; // Window height (extra for status)
    private static final int STATUS_HEIGHT = 30; // Height for status bar

    private final Board board; // Logical board
    private Seed currentPlayer;
    private State currentState;
    private final boolean isAIPlayerTurn;
    private final AIPlayerMinimax aiPlayer;

    private final JPanel gamePanel;
    private final JPanel controlPanel;
    private final JButton resetButton;
    private final JLabel statusLabel; // Status label for messages

    public TTTGraphic() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());

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
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT - STATUS_HEIGHT));
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
                        updateStatus();
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
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        resetButton = new JButton("Reset Game");
        resetButton.addActionListener(e -> newGame());
        controlPanel.add(new JLabel("Tic Tac Toe"));
        controlPanel.add(resetButton);

        // Setup status label
        statusLabel = new JLabel("Player X's Turn");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);
        statusLabel.setPreferredSize(new Dimension(WIDTH, STATUS_HEIGHT));

        // Add panels to this JPanel
        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private void aiMove() {
        if (currentState == State.PLAYING) {
            int[] move = aiPlayer.move();
            if (move != null) {
                currentState = board.stepGame(Seed.NOUGHT, move[0], move[1]);
                currentPlayer = Seed.CROSS;
                updateStatus();
                gamePanel.repaint();
            }
        }
    }

    private void newGame() {
        board.newGame();
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        updateStatus();
        gamePanel.repaint();
    }

    private void drawBoard(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(5)); // Thicker lines for grid

        // Draw grid
        g2d.setColor(Color.GRAY);
        for (int i = 1; i < SIZE; i++) {
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, HEIGHT - STATUS_HEIGHT);
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
    }

    private void updateStatus() {
        if (currentState == State.PLAYING) {
            statusLabel.setText(currentPlayer == Seed.CROSS ? "Player X's Turn" : "Player O's Turn");
        } else if (currentState == State.CROSS_WON) {
            statusLabel.setText("Player X Wins! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusLabel.setText("Player O Wins! Click to play again.");
        } else if (currentState == State.DRAW) {
            statusLabel.setText("It's a Draw! Click to play again.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new TTTGraphic());
        frame.pack();
        frame.setVisible(true);
    }
}
