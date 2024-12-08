import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import Sudoku.Sudoku;  // Untuk kelas Sudoku
//import TicTacToe.TTT;  // Untuk kelas TicTacToe
//import Othello.OthelloGame;  // Untuk kelas Othello

public class MainMenu extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainMenu() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Container untuk layout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Choose Your Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Calibre", Font.BOLD, 18));
        cp.add(titleLabel, BorderLayout.NORTH);

        // Panel dengan tombol untuk setiap game
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 1, 10, 10)); // Grid 3x1 untuk game

        // Tombol untuk setiap game
        JButton btnSudoku = new JButton("Sudoku");
        JButton btnTicTacToe = new JButton("Tic Tac Toe");
        JButton btnOthello = new JButton("Othello");

        gamePanel.add(btnSudoku);
        gamePanel.add(btnTicTacToe);
        gamePanel.add(btnOthello);

        cp.add(gamePanel, BorderLayout.CENTER);

        // Action listeners untuk tombol Sudoku
        btnSudoku.addActionListener(e -> {
            Sudoku sudokuGame = new Sudoku(); // Membuka game Sudoku
            sudokuGame.showDifficultyDialog(); // Menampilkan dialog tingkat kesulitan
            dispose(); // Menutup menu utama
        });

        // Action listeners untuk tombol Tic Tac Toe
        /*btnTicTacToe.addActionListener(e -> {
            TicTacToeGame ticTacToeGame = new TicTacToeGame(); // Membuka game Tic Tac Toe
            ticTacToeGame.setVisible(true); // Menampilkan jendela game Tic Tac Toe
            dispose(); // Menutup menu utama
        });

        // Action listeners untuk tombol Othello
        btnOthello.addActionListener(e -> {
            OthelloGame othelloGame = new OthelloGame(); // Membuka game Othello
            othelloGame.setVisible(true); // Menampilkan jendela game Othello
            dispose(); // Menutup menu utama
        });*/

        // Tombol Exit
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Keluar dari aplikasi
        cp.add(exitButton, BorderLayout.SOUTH);

        // Frame settings
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu()); // Menjalankan Main Menu
    }
}
