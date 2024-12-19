package TicTacToe;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents the Tic Tac Toe board and handles the game state.
 */
public class Board {
    public static final int ROWS = 3;  // Size of the board (3x3)
    public static final int COLS = 3;  // Size of the board (3x3)
    public Cell[][] cells;            // 2D array to hold the cells

    public Board() {
        cells = new Cell[ROWS][COLS];  // Initialize 3x3 board
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell();
            }
        }
    }

    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].content = Seed.NO_SEED;
            }
        }
    }

    public State stepGame(Seed currentPlayer, int row, int col) {
        cells[row][col].content = currentPlayer;

        if (checkWin(currentPlayer)) {
            return currentPlayer == Seed.CROSS ? State.CROSS_WON : State.NOUGHT_WON;
        }

        for (int rowCheck = 0; rowCheck < ROWS; rowCheck++) {
            for (int colCheck = 0; colCheck < COLS; colCheck++) {
                if (cells[rowCheck][colCheck].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }

        return State.DRAW;
    }

    private boolean checkWin(Seed player) {
        for (int row = 0; row < ROWS; row++) {
            if (cells[row][0].content == player && cells[row][1].content == player && cells[row][2].content == player) {
                return true;
            }
        }

        for (int col = 0; col < COLS; col++) {
            if (cells[0][col].content == player && cells[1][col].content == player && cells[2][col].content == player) {
                return true;
            }
        }

        if (cells[0][0].content == player && cells[1][1].content == player && cells[2][2].content == player) {
            return true;
        }
        if (cells[0][2].content == player && cells[1][1].content == player && cells[2][0].content == player) {
            return true;
        }

        return false;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        // Draw grid lines
        for (int row = 1; row < ROWS; row++) {
            g.drawLine(0, row * 120, COLS * 120, row * 120);
        }
        for (int col = 1; col < COLS; col++) {
            g.drawLine(col * 120, 0, col * 120, ROWS * 120);
        }

        // Paint each cell
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                cells[row][col].paint(g, col * 120, row * 120, 120); // Pass size as 120
            }
        }
    }
}
