//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class Cell {
    public static final int SIZE = 120;
    public static final int PADDING = 24;
    public static final int SEED_SIZE = 72;
    Seed content;
    int row;
    int col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED;
    }

    public void newGame() {
        this.content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        int x1 = this.col * 120 + 24;
        int y1 = this.row * 120 + 24;
        if (this.content == Seed.CROSS || this.content == Seed.NOUGHT) {
            g.drawImage(this.content.getImage(), x1, y1, 72, 72, (ImageObserver)null);
        }

    }

    public void paint(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 120, 120);
        if (this.content != Seed.NO_SEED) {
            g.drawImage(this.content.getImage(), x, y, 120, 120, (ImageObserver)null);
        }

    }
}