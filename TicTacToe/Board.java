package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public static final int ROWS = 3;  // Ukuran board 3x3
    public static final int COLS = 3;  // Ukuran board 3x3
    public Cell[][] cells;            // Array 2D untuk board

    public Board() {
        cells = new Cell[ROWS][COLS];  // Inisialisasi board 3x3
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell();
            }
        }
    }

    // Fungsi untuk memulai permainan baru (clear board)
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].content = Seed.NO_SEED; // Setel semua sel kosong
            }
        }
    }

    // Langkah permainan
    public State stepGame(Seed currentPlayer, int row, int col) {
        cells[row][col].content = currentPlayer;  // Isi sel dengan player yang bermain

        // Periksa apakah ada pemenang
        if (checkWin(currentPlayer)) {
            return currentPlayer == Seed.CROSS ? State.CROSS_WON : State.NOUGHT_WON;
        }

        // Periksa apakah ada posisi kosong lainnya
        for (int rowCheck = 0; rowCheck < ROWS; rowCheck++) {
            for (int colCheck = 0; colCheck < COLS; colCheck++) {
                if (cells[rowCheck][colCheck].content == Seed.NO_SEED) {
                    return State.PLAYING;  // Masih ada langkah yang bisa dimainkan
                }
            }
        }

        return State.DRAW;  // Jika tidak ada langkah yang tersisa, permainan seri
    }

    // Fungsi untuk memeriksa pemenang
    private boolean checkWin(Seed player) {
        // Periksa baris
        for (int row = 0; row < ROWS; row++) {
            if (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) {
                return true;
            }
        }

        // Periksa kolom
        for (int col = 0; col < COLS; col++) {
            if (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) {
                return true;
            }
        }

        // Periksa diagonal
        if (cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) {
            return true;
        }
        if (cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player) {
            return true;
        }

        return false;  // Tidak ada pemenang
    }

    // Fungsi untuk menggambar board ke layar (menambahkan metode paint)
    public void paint(Graphics g) {
        // Gambar grid board
        g.setColor(Color.BLACK);
        for (int row = 1; row < ROWS; row++) {
            g.drawLine(0, row * 120, 360, row * 120);  // Horizontal lines
        }
        for (int col = 1; col < COLS; col++) {
            g.drawLine(col * 120, 0, col * 120, 360);  // Vertical lines
        }
    }
}
