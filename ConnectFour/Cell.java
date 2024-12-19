package ConnectFour;

import java.awt.*;

public class Cell {
    public static final int SIZE = 100; // Ukuran satu sel
    public Seed content;               // Isi sel: CROSS, NOUGHT, atau kosong
    private int row, col;              // Posisi sel pada papan

    /** Konstruktor */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED; // Awalnya kosong
    }

    /** Menggambar isi sel di canvas */
    public void paint(Graphics g) {
        int x = col * SIZE;
        int y = row * SIZE;

        // Gambar kotak sel
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, SIZE, SIZE);

        // Gambar isi sel berdasarkan konten
        if (content == Seed.CROSS) {
            g.setColor(GameMain.COLOR_CROSS);
            g.drawLine(x + 10, y + 10, x + SIZE - 10, y + SIZE - 10);
            g.drawLine(x + 10, y + SIZE - 10, x + SIZE - 10, y + 10);
        } else if (content == Seed.NOUGHT) {
            g.setColor(GameMain.COLOR_NOUGHT);
            g.drawOval(x + 10, y + 10, SIZE - 20, SIZE - 20);
        }
    }

    /** Reset isi sel ke kosong */
    public void clear() {
        content = Seed.NO_SEED;
    }
}
