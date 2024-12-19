import Sudoku.Sudoku;
import TicTacToe.TTTGraphic;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

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
            JFrame frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new Sudoku()); // Pastikan Sudoku adalah JPanel
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose(); // Close main menu
        });

        // Action listeners for Tic Tac Toe button
        btnTicTacToe.addActionListener(e -> {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new TTTGraphic()); // Menambahkan TicTacToe JPanel ke JFrame
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose(); // Close main menu
        });

        // Action listeners for Othello button (jika Othello ditambahkan nanti)
        btnOthello.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Othello belum diimplementasikan!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
        });

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
