package TicTacToe;


import java.awt.Color;
import java.awt.Graphics;

public class Board {
    public static final int ROWS = 8;
    public static final int COLS = 8;
    public static final int CANVAS_WIDTH = 960;
    public static final int CANVAS_HEIGHT = 960;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = 4;
    public static final Color COLOR_GRID;
    public static final int Y_OFFSET = 1;
    Cell[][] cells;

    public Board() {
        this.initGame();
        cells = new Cell[ROWS][COLS]; // Create the array of cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col); // Initialize each cell
            }
        }
    }

    public void initGame() {
        this.cells = new Cell[8][8];

        for(int row = 0; row < 8; ++row) {
            for(int col = 0; col < 8; ++col) {
                this.cells[row][col] = new Cell(row, col);
            }
        }

    }

    public void newGame() {
        for(int row = 0; row < 8; ++row) {
            for(int col = 0; col < 8; ++col) {
                this.cells[row][col].newGame();
            }
        }

    }


    public void printBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                System.out.print(cells[row][col].content.getDisplayName());
                if (col < COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----");
            }
        }
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        this.cells[selectedRow][selectedCol].content = player;
        if ((this.cells[selectedRow][0].content != player || this.cells[selectedRow][1].content != player || this.cells[selectedRow][2].content != player) && (this.cells[0][selectedCol].content != player || this.cells[1][selectedCol].content != player || this.cells[2][selectedCol].content != player) && (selectedRow != selectedCol || this.cells[0][0].content != player || this.cells[1][1].content != player || this.cells[2][2].content != player) && (selectedRow + selectedCol != 2 || this.cells[0][2].content != player || this.cells[1][1].content != player || this.cells[2][0].content != player)) {
            for(int row = 0; row < 8; ++row) {
                for(int col = 0; col < 8; ++col) {
                    if (this.cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING;
                    }
                }
            }

            return State.DRAW;
        } else {
            return player == Seed.CROSS ? State.CROSS_WON : State.NOUGHT_WON;
        }
    }

    public void paint(Graphics g) {
        g.setColor(COLOR_GRID);

        int row;
        for(row = 1; row < 8; ++row) {
            g.fillRoundRect(0, 120 * row - 4, 959, 8, 8, 8);
        }

        for(row = 1; row < 8; ++row) {
            g.fillRoundRect(120 * row - 4, 1, 8, 959, 8, 8);
        }

        for(row = 0; row < 8; ++row) {
            for(int col = 0; col < 8; ++col) {
                this.cells[row][col].paint(g);
            }
        }

    }

    static {
        COLOR_GRID = Color.LIGHT_GRAY;
    }
}