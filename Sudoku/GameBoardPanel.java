package Sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // To prevent serial warning
    private int score = 0;
    private JLabel scoreLabel;

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    // Define properties
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();
    private boolean isPaused = false;

    public void setPaused(boolean isPaused){
        this.isPaused = isPaused;
    }

    @Override
    public void processMouseEvent(MouseEvent e) {
        if (!isPaused) {
            super.processMouseEvent(e); //  interaksi kalau nggak paused
        }
    }

    /** Constructor */
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

    public void pauseGame() {
        isPaused = !isPaused;
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].setEnabled(!isPaused);
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

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            try {
                int numberIn = Integer.parseInt(sourceCell.getText());
                System.out.println("You entered " + numberIn);

                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    updateScore(10);
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    updateScore(-5);
                }
                sourceCell.paint();

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
