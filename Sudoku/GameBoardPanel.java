package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L; // To prevent serial warning
    private int score = 0;
    private JLabel scoreLabel;
    private boolean isDark = false;

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60; // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    // Define properties
    private final Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private final Puzzle puzzle = new Puzzle();
    private boolean isPaused = false;

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    // Add method to retrieve a specific cell by row and column
    public Cell getCell(int row, int col) {
        if (row >= 0 && row < SudokuConstants.GRID_SIZE && col >= 0 && col < SudokuConstants.GRID_SIZE) {
            return cells[row][col];
        } else {
            throw new IllegalArgumentException("Invalid cell position: (" + row + ", " + col + ")");
        }
    }

    /**
     * Constructor
     */
    public GameBoardPanel() {
        super.setLayout(new BorderLayout());

        // Create a grid for cells
        JPanel cellPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                cellPanel.add(cells[row][col]);
            }
        }
        super.add(cellPanel, BorderLayout.CENTER);

        // Add listener for editable cells
        CellInputListener listener = new CellInputListener();
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    private void updateScore(int delta) {
        score += delta;
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
            scoreLabel.revalidate();
            scoreLabel.repaint();
        }
    }

    public void newGame(String difficulty) {
        puzzle.newPuzzle(difficulty);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        score = 0;
        updateScore(0);
    }

    public void resetGame() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (!puzzle.isGiven[row][col]) {
                    cells[row][col].setText("");
                    cells[row][col].status = CellStatus.TO_GUESS;
                    cells[row][col].paint();
                }
            }
        }
        score = 0;
        updateScore(0);
    }

    public String getHint() {
        int targetRow = 2;
        int targetCol = 3;
        int suggestedNumber = 5;

        boolean isGreater = Math.random() > 0.5;
        String hintMessage = "Try placing " + suggestedNumber + " at row " + targetRow + ", column " + targetCol + ".";
        if (isGreater) {
            hintMessage += " The correct number is greater than " + suggestedNumber + ".";
        } else {
            hintMessage += " The correct number is less than " + suggestedNumber + ".";
        }

        return hintMessage;
    }

    public void setDarkMode(boolean isDark) {
        this.isDark = isDark;
        if (isDark) {
            setBackground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
        }
        repaint(); // Repaint untuk memperbarui tampilan
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidMove(int row, int col, int number) {
        // Periksa apakah angka tersebut sudah ada di baris yang sama
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (cells[row][i].number == number) {
                return false;
            }
        }

        // Periksa apakah angka tersebut sudah ada di kolom yang sama
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (cells[i][col].number == number) {
                return false;
            }
        }

        // Periksa apakah angka tersebut sudah ada di kotak 3x3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int i = boxRowStart; i < boxRowStart + 3; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (cells[i][j].number == number) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkAnswer(int row, int col, int numberIn) {
        return cells[row][col].number == numberIn;
    }

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            try {
                int numberIn = Integer.parseInt(sourceCell.getText());
                System.out.println("You entered " + numberIn);

                // Cek apakah jawaban benar atau salah
                if (checkAnswer(sourceCell.row, sourceCell.col, numberIn)) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    updateScore(10); // Tambahkan 10 poin jika jawaban benar
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    updateScore(-5); // Kurangi 5 poin jika jawaban salah
                }

                sourceCell.paint(); // Perbarui tampilan status sel

                if (isSolved()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!", "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                sourceCell.setText("");
            }
        }
    }
}
