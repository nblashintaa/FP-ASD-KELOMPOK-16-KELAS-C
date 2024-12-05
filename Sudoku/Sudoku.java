package Sudoku;
import java.awt.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JTextField statusBar = new JTextField("Welcome to Sudoku!");

    // Constructor
    public Sudoku() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);
        // Add a button to the south to re-start the game via board.newGame()
        cp.add(btnNewGame, BorderLayout.SOUTH);
        // Initialize the game board to start the game
        board.newGame();

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

        btnNewGame.addActionListener(e -> board.newGame());
        newGame.addActionListener(e -> board.newGame());
        resetGame.addActionListener(e -> board.resetGame());
        exit.addActionListener(e -> System.exit(0));
        hintButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Hint feature is not yet implemented!", "Hint", JOptionPane.INFORMATION_MESSAGE);
        });
        pauseButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pause feature is not yet implemented!", "Hint", JOptionPane.INFORMATION_MESSAGE);
        });

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

}