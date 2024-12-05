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
    private static final long serialVersionUID = 1L;// to prevent serial warning
    private int timeElapsed = 0;
    private int score = 0;
    private Timer timer;
    private JLabel timerLabel;
    private boolean isPaused = false;
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JTextField statusBar = new JTextField("Welcome to Sudoku!");
    private JButton[][] kotakSudoku = new JButton[9][9];


    private boolean isEmptyCell(int row, int col) {
        return kotakSudoku[row][col] != null && kotakSudoku[row][col].getText().isEmpty();
    }

    private JLabel scoreLabel;

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);
        // Add a button to the south to re-start the game via board.newGame()
        cp.add(btnNewGame, BorderLayout.SOUTH);
        // Initialize the game board to start the game
        board.newGame("easy");

        //panel samping
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        JButton hintButton = new JButton("Hint");
        JButton pauseButton = new JButton("Pause");
        sidePanel.add(hintButton);
        sidePanel.add(pauseButton);
        cp.add(sidePanel, BorderLayout.EAST);
        //menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem resetGame = new JMenuItem("Reset Game");
        JMenuItem pauseGame = new JMenuItem("Game Paused");
        JMenuItem exit = new JMenuItem("Exit");
        menuFile.add(newGame);
        menuFile.add(resetGame);
        menuFile.add(pauseGame);
        menuFile.add(exit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        btnNewGame.setOpaque(true);
        btnNewGame.setBorderPainted(false);
        btnNewGame.setBackground(new Color(51, 153, 255));
        btnNewGame.setBackground(Color.PINK); // Mengatur warna tombol menjadi pink


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

        scoreLabel = new JLabel("Score: 0");
        sidePanel.add(scoreLabel); // Tambahkan ke panel

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

        //buat timer ya ges ya
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

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
        showWelcomeDialog();
    }

    private void showWelcomeDialog() {
        JDialog welcomeDialog = new JDialog(this, "Welcome to Sudoku!", true);
        welcomeDialog.setLayout(new BorderLayout());
        welcomeDialog.setSize(400, 200);
        welcomeDialog.setLocationRelativeTo(null); // Center

        JLabel message = new JLabel("Welcome to Sudoku! Get ready to play!", JLabel.CENTER);
        JButton btnStart = new JButton("Start Game");
        btnStart.addActionListener(e -> welcomeDialog.dispose());

        welcomeDialog.add(message, BorderLayout.CENTER);
        welcomeDialog.add(btnStart, BorderLayout.SOUTH);
        welcomeDialog.setVisible(true);
        System.out.println("Showing Welcome Dialog...");
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

    private void pauseGame() {
        if (timer != null) {
            timer.stop(); // Hentikan timer
        }
    }

    private void resumeGame() {
        if (timer != null) {
            timer.start(); // Lanjutkan timer
        }
    }

    public void validateInput(int row, int col, int value) {
        if (isPaused) {
            JOptionPane.showMessageDialog(this, "Game is paused!", "Pause", JOptionPane.WARNING_MESSAGE);
            return;
        }
    }
}