package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TicTacToe extends JPanel {
    private static final int GRID_SIZE = 3;
    private static final int CELL_SIZE = 150;
    private static final int BOARD_SIZE = CELL_SIZE * GRID_SIZE;
    private static final int SYMBOL_STROKE_WIDTH = 8;
    public static final Color COLOR_CROSS = Color.RED;
    public static final Color COLOR_NOUGHT = Color.BLUE;
    private char[][] board = new char[GRID_SIZE][GRID_SIZE];
    private boolean xTurn = true;
    private String statusMessage = "X's Turn";

    public TicTacToe() {
        this.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE + 50));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;
                if (row < GRID_SIZE && col < GRID_SIZE && board[row][col] == '\0') {
                    board[row][col] = xTurn ? 'X' : 'O';
                    if (checkWin(row, col)) {
                        statusMessage = (xTurn ? "X" : "O") + " Won! Click to play again.";
                        repaint();
                        return;
                    }
                    xTurn = !xTurn;
                    statusMessage = xTurn ? "X's Turn" : "O's Turn";
                    repaint();
                } else if (row >= GRID_SIZE) { // Reset on click below board
                    resetGame();
                }
            }
        });
    }

    private void resetGame() {
        board = new char[GRID_SIZE][GRID_SIZE];
        xTurn = true;
        statusMessage = "X's Turn";
        repaint();
    }

    private boolean checkWin(int row, int col) {
        char symbol = board[row][col];
        // Check row
        if (board[row][0] == symbol && board[row][1] == symbol && board[row][2] == symbol) return true;
        // Check column
        if (board[0][col] == symbol && board[1][col] == symbol && board[2][col] == symbol) return true;
        // Check diagonals
        if (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) return true;
        if (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol) return true;
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw grid
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(5));
        for (int i = 1; i < GRID_SIZE; i++) {
            g2d.drawLine(0, i * CELL_SIZE, BOARD_SIZE, i * CELL_SIZE);
            g2d.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, BOARD_SIZE);
        }

        // Draw symbols
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 4;
                int y = row * CELL_SIZE + CELL_SIZE / 4;
                if (board[row][col] == 'X') {
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH));
                    g2d.drawLine(col * CELL_SIZE + 20, row * CELL_SIZE + 20, (col + 1) * CELL_SIZE - 20, (row + 1) * CELL_SIZE - 20);
                    g2d.drawLine((col + 1) * CELL_SIZE - 20, row * CELL_SIZE + 20, col * CELL_SIZE + 20, (row + 1) * CELL_SIZE - 20);
                } else if (board[row][col] == 'O') {
                    g2d.setColor(Color.BLUE);
                    g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH));
                    g2d.drawOval(col * CELL_SIZE + 20, row * CELL_SIZE + 20, CELL_SIZE - 40, CELL_SIZE - 40);
                }
            }
        }

        // Draw status message
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        g2d.drawString(statusMessage, 10, BOARD_SIZE + 30);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TicTacToe());
        frame.pack();
        frame.setVisible(true);
    }
}
