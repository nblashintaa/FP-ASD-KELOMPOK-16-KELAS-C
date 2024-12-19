package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {

    public Seed content;

    public Cell() {
        this.content = Seed.NO_SEED;
    }

    public void paint(Graphics g, int x, int y, int size) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
        if (content == Seed.CROSS) {
            g.setColor(TicTacToe.COLOR_CROSS);
            g.drawLine(x, y, x + size, y + size);
            g.drawLine(x, y + size, x + size, y);
        } else if (content == Seed.NOUGHT) {
            g.setColor(TicTacToe.COLOR_NOUGHT);
            g.drawOval(x + 10, y + 10, size - 20, size - 20);
        }
    }
}
