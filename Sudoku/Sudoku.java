package Sudoku;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;
    private int timeElapsed = 0;
    private int score = 0;
    private Timer timer;
    private JLabel timerLabel;
    private boolean isPaused = false;
    private boolean isDarkMode = false;
    private CountdownTimer countdownTimer; // Timer countdown
    private JLabel countdownTimerLabel;
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JTextField statusBar = new JTextField("Welcome to Sudoku!");
    private JLabel scoreLabel;
    private JButton darkModeButton;

    public Sudoku() {
        SoundEffect backSound = new SoundEffect("sudoku/bensound-clearday.wav");
        backSound.play(); // Memulai backsound
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

        JMenu optionMenu = new JMenu("Option");

        JMenuItem toggleSoundItem = new JMenuItem("Toggle Sound");
        toggleSoundItem.addActionListener(new ActionListener() {
            private boolean isPlaying = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying) {
                    backSound.stop(); // Hentikan audio
                    isPlaying = false;
                } else {
                    backSound.play(); // Putar ulang audio
                    isPlaying = true;
                }
            }
        });

        // Add Dark Mode button
        darkModeButton = new JButton("Dark Mode");
        darkModeButton.addActionListener(e -> toggleDarkMode());
        sidePanel.add(darkModeButton);

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
            if (!isPaused) {
                String hint = board.getHint();
                if (hint != null) {
                    JOptionPane.showMessageDialog(this, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "No hints available!", "Hint", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Game is paused!", "Hint", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Pause button logic
        pauseButton.addActionListener(e -> togglePause(pauseButton));

        // Timer setup
        timerLabel = new JLabel("Time: 0", JLabel.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(timerLabel, BorderLayout.NORTH);

        // Countdown timer setup
        countdownTimerLabel = new JLabel("Time Left: 0", JLabel.CENTER);
        countdownTimerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(countdownTimerLabel, BorderLayout.NORTH);
        countdownTimer = new CountdownTimer(countdownTimerLabel, this);

        // Main timer
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

    private void togglePause(JButton pauseButton) {
        if (!isPaused) {
            // Pause both timers
            timer.stop();
            countdownTimer.stopTimer();
            disableGameBoard(true);
            board.setPaused(true);
            pauseButton.setText("Resume");
            isPaused = true;
        } else {
            // Resume both timers
            timer.start();
            countdownTimer.startTimer();
            disableGameBoard(false);
            board.setPaused(false);
            pauseButton.setText("Pause");
            isPaused = false;
        }
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        updateDarkMode();
    }

    private void updateDarkMode() {
        if (isDarkMode) {
            getContentPane().setBackground(Color.BLACK);
            timerLabel.setForeground(Color.WHITE);
            scoreLabel.setForeground(Color.WHITE);
            darkModeButton.setText("Light Mode");
            countdownTimerLabel.setForeground(Color.WHITE);
        } else {
            getContentPane().setBackground(Color.WHITE);
            timerLabel.setForeground(Color.BLACK);
            scoreLabel.setForeground(Color.BLACK);
            darkModeButton.setText("Dark Mode");
            countdownTimerLabel.setForeground(Color.BLACK);
        }
        repaint();
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

    public void showDifficultyDialog() {
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
            board.newGame(difficulty.toLowerCase()); // Mulai permainan baru
            countdownTimer.setTime(difficulty.toLowerCase()); // Set waktu countdown sesuai kesulitan
            countdownTimer.startTimer(); // Mulai countdown timer
        }
    }

    void disableGameBoard(boolean disable) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                Cell cell = board.getCell(row, col);
                if (cell != null) {
                    cell.setEnabled(!disable);
                }
            }
        }
    }
}
