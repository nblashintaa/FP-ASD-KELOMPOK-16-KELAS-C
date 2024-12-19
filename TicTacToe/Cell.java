package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 * Represents a single cell in the Tic Tac Toe board.
 */
public class Cell {

    // Content of the cell: CROSS, NOUGHT, or NO_SEED
    public Seed content;

    // Constructor: Initializes the cell as empty
    public Cell() {
        this.content = Seed.NO_SEED;
    }

    /**
     * Paints the cell on the board.
     *
     * @param g    Graphics object used for rendering.
     * @param x    X-coordinate of the cell.
     * @param y    Y-coordinate of the cell.
     * @param size Size of the cell (width and height).
     */
    public void paint(Graphics g, int x, int y, int size) {
        Graphics2D g2d = (Graphics2D) g; // Use Graphics2D for enhanced styling

        // Draw the border of the cell with a thicker stroke
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(5)); // Thicker border
        g2d.drawRect(x, y, size, size);

        // Draw the content (X or O)
        if (content == Seed.CROSS) {
            g2d.setColor(TicTacToe.COLOR_CROSS); // Use color from TicTacToe (red)
            g2d.setStroke(new BasicStroke(8)); // Thicker line for "X"
            g2d.drawLine(x + 20, y + 20, x + size - 20, y + size - 20); // Diagonal 1
            g2d.drawLine(x + 20, y + size - 20, x + size - 20, y + 20); // Diagonal 2
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(TicTacToe.COLOR_NOUGHT); // Use color from TicTacToe (blue)
            g2d.setStroke(new BasicStroke(8)); // Thicker stroke for "O"
            g2d.drawOval(x + 20, y + 20, size - 40, size - 40); // Circle with padding
        }
    }
}
