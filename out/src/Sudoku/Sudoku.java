package Sudoku;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;
    private int timeElapsed = 0;
    private int score = 0;
    private Timer timer;
    private JLabel timerLabel;
    private boolean isPaused = false;
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JTextField statusBar = new JTextField("Welcome to Sudoku!");
    private JButton[][] kotakSudoku = new JButton[9][9];

    private JLabel scoreLabel;

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);
        cp.add(btnNewGame, BorderLayout.SOUTH);
        board.newGame("easy");

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        JButton hintButton = new JButton("Hint");
        JButton pauseButton = new JButton("Pause");
        sidePanel.add(hintButton);
        sidePanel.add(pauseButton);

        // Create score label and add to the side panel
        scoreLabel = new JLabel("Score: 0");
        sidePanel.add(scoreLabel);

        cp.add(sidePanel, BorderLayout.EAST);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem resetGame = new JMenuItem("Reset Game");
        JMenuItem exit = new JMenuItem("Exit");
        menuFile.add(newGame);
        menuFile.add(resetGame);
        menuFile.add(exit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        btnNewGame.addActionListener(e -> showDifficultyDialog());
        newGame.addActionListener(e -> showDifficultyDialog());
        resetGame.addActionListener(e -> board.resetGame());
        exit.addActionListener(e -> System.exit(0));
        hintButton.addActionListener(e -> {
            String hint = board.getHint();
            if (hint != null) {
                JOptionPane.showMessageDialog(this, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No hints available!", "Hint", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        pauseButton.addActionListener(e -> {
            if (!isPaused) {
                timer.stop();
                pauseButton.setText("Resume");
                isPaused = true;
            } else {
                timer.start();
                pauseButton.setText("Pause");
                isPaused = false;
            }
        });

        timerLabel = new JLabel("Time: 0", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel, BorderLayout.NORTH);
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    timeElapsed++;
                    timerLabel.setText("Time: " + timeElapsed);
                }
            }
        });
        timer.start();

        // Link scoreLabel to GameBoardPanel
        board.setScoreLabel(scoreLabel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
        showWelcomeDialog();
    }

    private void showWelcomeDialog() {
        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku!", true);
        welcomeDialog.setLayout(new BorderLayout());
        welcomeDialog.setSize(400, 200);
        welcomeDialog.setLocationRelativeTo(null);

        JLabel message = new JLabel("Welcome to Sudoku! Get ready to play!", JLabel.CENTER);
        JButton btnStart = new JButton("Start Game");
        btnStart.addActionListener(e -> welcomeDialog.dispose());

        welcomeDialog.add(message, BorderLayout.CENTER);
        welcomeDialog.add(btnStart, BorderLayout.SOUTH);
        welcomeDialog.setVisible(true);
    }

    private void showDifficultyDialog() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty Level:",
                "New Game Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );

        if (choice >= 0) {
            String difficulty = options[choice];
            board.newGame(difficulty.toLowerCase());
        }
    }

    private void updateScore(int points) {
        score += points;
        if (score < 0) score = 0; // Prevent negative scores
        scoreLabel.setText("Score: " + score);
    }

    public void validateInput(int row, int col, int value) {
        if (isPaused) {
            JOptionPane.showMessageDialog(this, "Game is paused!", "Pause", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean isCorrect = board.checkAnswer(row, col, value);
        if (isCorrect) {
            updateScore(10); // Add 10 points for correct answer
            JOptionPane.showMessageDialog(this, "Correct!", "Input Validation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            updateScore(-5); // Deduct 5 points for incorrect answer
            JOptionPane.showMessageDialog(this, "Incorrect!", "Input Validation", JOptionPane.ERROR_MESSAGE);
        }
    }
}
