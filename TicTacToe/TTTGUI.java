package TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TTTGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JButton[][] buttons;
    private Board board;
    private Seed currentPlayer;
    private State currentState;

    public TTTGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Initialize the board and game state
        board = new Board();
        currentPlayer = Seed.CROSS;  // CROSS starts first
        currentState = State.PLAYING;

        // Create layout and buttons
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Tic Tac Toe", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        cp.add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));  // 3x3 grid for the board

        buttons = new JButton[Board.ROWS][Board.COLS];
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                gamePanel.add(buttons[row][col]);
            }
        }

        cp.add(gamePanel, BorderLayout.CENTER);
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentState == State.PLAYING && board.cells[row][col].content == Seed.NO_SEED) {
                // Player makes a move
                board.cells[row][col].content = currentPlayer;


                // Check for the game state after the move
                currentState = board.stepGame(currentPlayer, row, col);
                if (currentState == State.CROSS_WON) {
                    JOptionPane.showMessageDialog(null, "'X' won!");
                } else if (currentState == State.NOUGHT_WON) {
                    JOptionPane.showMessageDialog(null, "'O' won!");
                } else if (currentState == State.DRAW) {
                    JOptionPane.showMessageDialog(null, "It's a Draw!");
                }

                // Switch player
                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TTTGUI game = new TTTGUI();
            game.setVisible(true);
        });
    }
}