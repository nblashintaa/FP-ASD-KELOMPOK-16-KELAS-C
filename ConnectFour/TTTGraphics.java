package ConnectFour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TTTGraphics extends JFrame {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static final int CELL_SIZE = 100;
    private static final int GRID_WIDTH = 8;
    private static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    private static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    private static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    private ConnectFour game; // Reference to ConnectFour game
    private final JPanel gamePanel;
    private final JLabel statusBar;

    private Seed currentPlayer; // Current player
    private State currentState; // Current game state
    private String difficultyLevel; // Difficulty level selected by the user

    public TTTGraphics() {
        // Show a welcome pop-up and select difficulty level
        JOptionPane.showMessageDialog(null, "Welcome to Connect Four Game!\nChoose your difficulty level:");

        String[] difficultyLevels = {"Easy", "Medium", "Hard"};
        difficultyLevel = (String) JOptionPane.showInputDialog(null, "Select Difficulty",
                "Difficulty Level", JOptionPane.QUESTION_MESSAGE, null, difficultyLevels, difficultyLevels[1]);

        // Initialize ConnectFour game with human and AI names
        game = new ConnectFour("Player 1", "AI (" + difficultyLevel + ")");

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.WHITE);

                g.setColor(Color.LIGHT_GRAY);
                for (int row = 1; row < ROWS; row++) {
                    g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF, CANVAS_WIDTH, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
                }
                for (int col = 1; col < COLS; col++) {
                    g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, CANVAS_HEIGHT, GRID_WIDTH, GRID_WIDTH);
                }

                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLS; col++) {
                        int x1 = col * CELL_SIZE + GRID_WIDTH_HALF;
                        int y1 = row * CELL_SIZE + GRID_WIDTH_HALF;
                        if (game.getBoard()[row][col] == ConnectFour.HUMAN_PLAYER) {
                            g.setColor(Color.RED);
                            g.fillOval(x1, y1, CELL_SIZE - GRID_WIDTH, CELL_SIZE - GRID_WIDTH);
                        } else if (game.getBoard()[row][col] == ConnectFour.AI_PLAYER) {
                            g.setColor(Color.BLUE);
                            g.fillOval(x1, y1, CELL_SIZE - GRID_WIDTH, CELL_SIZE - GRID_WIDTH);
                        }
                    }
                }
            }
        };

        gamePanel.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;

                if (currentState == State.PLAYING && currentPlayer == Seed.PLAYER1) {
                    if (game.isValidMove(col)) {
                        game.makeMove(col, ConnectFour.HUMAN_PLAYER);
                        currentState = checkGameState();
                        currentPlayer = Seed.PLAYER2;

                        // AI's turn
                        if (currentState == State.PLAYING) {
                            int bestMove = game.getBestMove();
                            game.makeMove(bestMove, ConnectFour.AI_PLAYER);
                            currentState = checkGameState();
                            currentPlayer = Seed.PLAYER1;
                        }
                    }
                    repaint();
                } else {
                    resetGame();
                    repaint();
                }
            }
        });

        statusBar = new JLabel(" ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

        setLayout(new BorderLayout());
        add(gamePanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("Connect Four");
        setVisible(true);

        resetGame();
    }

    private void resetGame() {
        game = new ConnectFour("Player 1", "AI (" + difficultyLevel + ")");
        currentPlayer = Seed.PLAYER1;
        currentState = State.PLAYING;
        statusBar.setText("Player 1's Turn");
    }

    private State checkGameState() {
        if (game.isWinningMove(game.getBoard(), ConnectFour.HUMAN_PLAYER)) {
            statusBar.setText("Player 1 Wins!");
            return State.WIN;
        } else if (game.isWinningMove(game.getBoard(), ConnectFour.AI_PLAYER)) {
            statusBar.setText("Computer Wins!");
            return State.WIN;
        } else if (game.isDraw()) {
            statusBar.setText("It's a Draw!");
            return State.DRAW;
        } else {
            statusBar.setText("Computer's Turn");
            return State.PLAYING;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TTTGraphics::new);
    }
}
