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

    public void setDarkMode(boolean isDark) {
        this.isDark = isDark;
        if (isDark) {
            setBackground(Color.BLACK);
        } else {
            setBackground(Color.WHITE);
        }
        repaint(); // Repaint untuk memperbarui tampilan
    }

    public void applyTheme(boolean isDarkMode) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                Cell cell = getCell(row, col);
                if (cell != null) {
                    cell.setDarkMode(isDarkMode); // Set dark mode for each cell
                }
            }
        }
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

    public boolean checkAnswer(int row, int col, int numberIn) {
        return cells[row][col].number == numberIn;
    }

    // Method to provide a hint
    public String getHint() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                // Check if the cell is empty and not given
                if (cells[row][col].getText().isEmpty() && !puzzle.isGiven[row][col]) {
                    return "Hint: Try filling the cell at (" + row + ", " + col + ")";
                }
            }
        }
        return "No more hints available!";  // No hint available if no empty cell
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
