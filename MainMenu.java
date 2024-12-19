import Sudoku.Sudoku;
import TicTacToe.GameMain;

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
            new Sudoku(); // Membuka game Sudoku, dialog akan otomatis muncul
            dispose(); // Menutup menu utama
        });

        // Action listeners untuk tombol Tic Tac Toe
        btnTicTacToe.addActionListener(e -> {
            new GameMain();
            dispose(); // Menutup menu utama
        });

        // Action listeners untuk tombol Othello
        /*btnOthello.addActionListener(e -> {
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
        SwingUtilities.invokeLater(MainMenu::new); // Menjalankan Main Menu
    }
}
