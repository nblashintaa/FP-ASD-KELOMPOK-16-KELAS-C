import Sudoku.Sudoku;
import TicTacToe.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
//import Othello.OthelloGame;  // Untuk kelas Othello

public class MainMenu extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public MainMenu() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Container for layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Choose Your Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibre", Font.BOLD, 18));
        cp.add(titleLabel, BorderLayout.NORTH);

        // Panel with buttons for each game
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3x1 grid for games

        // Buttons for each game
        JButton btnSudoku = new JButton("Sudoku");
        JButton btnTicTacToe = new JButton("Tic Tac Toe");
        JButton btnOthello = new JButton("Othello");

        gamePanel.add(btnSudoku);
        gamePanel.add(btnTicTacToe);
        gamePanel.add(btnOthello);

        cp.add(gamePanel, BorderLayout.CENTER);

        // Action listeners for Sudoku button
        btnSudoku.addActionListener(e -> {
            new Sudoku(); // Open Sudoku game, dialog will appear
            dispose(); // Close main menu
        });

        // Action listeners for Tic Tac Toe button
        btnTicTacToe.addActionListener(e -> {
            new TicTacToe(); // Open TicTacToe GUI
            dispose(); // Close main menu
        });

        // Action listeners for Othello button (if needed)
        /*btnOthello.addActionListener(e -> {
            OthelloGame othelloGame = new OthelloGame(); // Open Othello game
            othelloGame.setVisible(true); // Display Othello game window
            dispose(); // Close main menu
        });*/

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Exit application
        cp.add(exitButton, BorderLayout.SOUTH);

        // Frame settings
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new); // Run the Main Menu
    }
}