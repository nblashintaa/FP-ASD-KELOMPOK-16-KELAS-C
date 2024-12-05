package Sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;// to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JTextField statusBar = new JTextField("Welcome to Sudoku!");
    private JButton[][] kotakSudoku = new JButton[9][9];
    private boolean isEmptyCell(int row, int col) {
        return kotakSudoku[row][col] != null && kotakSudoku[row][col].getText().isEmpty();
    }



    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        btnNewGame.setOpaque(true);
        btnNewGame.setBorderPainted(false);
        btnNewGame.setBackground(new Color(51, 153, 255));
        btnNewGame.setBackground(Color.PINK); // Mengatur warna tombol menjadi pink

        // Menentukan warna tertentu untuk kotak yang kosong
        for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (isEmptyCell(row, col)) {
                        kotakSudoku[row][col].setBackground(Color.PINK);
                    }
                }
        }

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
            JOptionPane.showMessageDialog(this, "Pause feature is not yet implemented!", "Pause", JOptionPane.INFORMATION_MESSAGE);
        });

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
        showWelcomeDialog();

        btnNewGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNewGame.setBackground(new Color(30, 144, 255)); // Highlight
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNewGame.setBackground(new Color(51, 153, 255)); // Warna default
            }
        });
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
}
