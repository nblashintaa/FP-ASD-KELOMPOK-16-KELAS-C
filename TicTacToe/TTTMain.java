package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTMain extends JFrame {
    private static final long serialVersionUID = 1L;

    public TTTMain() {
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

        btnTicTacToe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open TicTacToe game window when clicked
                new TTTGUI().setVisible(true);
            }
        });

        gamePanel.add(btnSudoku);
        gamePanel.add(btnTicTacToe);
        gamePanel.add(btnOthello);

        cp.add(gamePanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TTTMain menu = new TTTMain();
            menu.setVisible(true);
        });
    }
}
