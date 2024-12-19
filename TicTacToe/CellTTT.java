package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

/**
 * Represents a single cell in the Tic Tac Toe board.
 */
public class CellTTT {

    // Content of the cell: CROSS, NOUGHT, or NO_SEED
    public Seed content;

    // Constructor: Initializes the cell as empty
    public CellTTT() {
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

        // Draw the border of the cell
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(4)); // Slightly thinner border
        g2d.drawRect(x, y, size, size); // Draw the cell border

        // Draw the content (X or O)
        int padding = size / 5; // Larger padding for better scaling
        if (content == Seed.CROSS) {
            g2d.setColor(TicTacToe.COLOR_CROSS);
            g2d.setStroke(new BasicStroke(6)); // Thicker stroke for "X"
            g2d.drawLine(x + padding, y + padding, x + size - padding, y + size - padding); // Diagonal 1
            g2d.drawLine(x + padding, y + size - padding, x + size - padding, y + padding); // Diagonal 2
        } else if (content == Seed.NOUGHT) {
            g2d.setColor(TicTacToe.COLOR_NOUGHT);
            g2d.setStroke(new BasicStroke(6)); // Thicker stroke for "O"
            g2d.drawOval(x + padding, y + padding, size - 2 * padding, size - 2 * padding); // Circle with padding
        }
    }
}
