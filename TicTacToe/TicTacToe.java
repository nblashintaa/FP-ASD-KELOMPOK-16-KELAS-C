package TicTacToe;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

    public class TicTacToe extends JPanel {
        private static final long serialVersionUID = 1L;
        public static final String TITLE = "Tic Tac Toe";
        public static final Color COLOR_BG = Color.WHITE;
        public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
        public static final Color COLOR_CROSS = new Color(239, 105, 80);
        public static final Color COLOR_NOUGHT = new Color(64, 154, 225);
        public static final Font FONT_STATUS = new Font("OCR A Extended", 0, 14);
        private Board board;
        private State currentState;
        private Seed currentPlayer;
        private JLabel statusBar;

        // Gambar X dan O
        private Image crossImage;
        private Image noughtImage;

        // Variable for game level
        private static String gameLevel = "Easy"; // Default level

        public TicTacToe() {
            super.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    int row = mouseY / 120;
                    int col = mouseX / 120;
                    if (TicTacToe.this.currentState == State.PLAYING) {
                        if (row >= 0 && row < 3 && col >= 0 && col < 3 && TicTacToe.this.board.cells[row][col].content == Seed.NO_SEED) {
                            TicTacToe.this.currentState = TicTacToe.this.board.stepGame(TicTacToe.this.currentPlayer, row, col);
                            TicTacToe.this.currentPlayer = TicTacToe.this.currentPlayer == Seed.CROSS ? Seed.NOUGHT : Seed.CROSS;
                        }
                    } else {
                        TicTacToe.this.resetGame();
                    }

                    TicTacToe.this.repaint(); // Pastikan untuk memanggil repaint agar GUI diperbarui
                }
            });
            this.statusBar = new JLabel();
            this.statusBar.setFont(FONT_STATUS);
            this.statusBar.setBackground(COLOR_BG_STATUS);
            this.statusBar.setOpaque(true);
            this.statusBar.setPreferredSize(new Dimension(300, 30));
            this.statusBar.setHorizontalAlignment(2);
            this.statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
            super.setLayout(new BorderLayout());
            super.add(this.statusBar, "Last");
            super.setPreferredSize(new Dimension(960, 990));
            super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));
            this.initGame();
            this.resetGame();
        }

        public void initGame() {
            this.board = new Board();
            // Memuat gambar X dan O
            this.crossImage = new ImageIcon("path_to_cross_image.png").getImage(); // Pastikan path benar
            this.noughtImage = new ImageIcon("path_to_nought_image.png").getImage(); // Pastikan path benar
        }

        public void resetGame() {
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 3; ++col) {
                    this.board.cells[row][col].content = Seed.NO_SEED;
                }
            }

            this.currentPlayer = Seed.CROSS;
            this.currentState = State.PLAYING;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(COLOR_BG);
            this.board.paint(g);

            // Gambar X dan O sesuai dengan posisi mereka
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.cells[row][col].content == Seed.CROSS) {
                        g.drawImage(crossImage, col * 120, row * 120, null); // Gambar X
                    } else if (board.cells[row][col].content == Seed.NOUGHT) {
                        g.drawImage(noughtImage, col * 120, row * 120, null); // Gambar O
                    }
                }
            }

            if (this.currentState == State.PLAYING) {
                this.statusBar.setForeground(Color.BLACK);
                this.statusBar.setText(this.currentPlayer == Seed.CROSS ? "X's Turn" : "O's Turn");
            } else if (this.currentState == State.DRAW) {
                this.statusBar.setForeground(Color.RED);
                this.statusBar.setText("It's a Draw! Click to play again.");
            } else if (this.currentState == State.CROSS_WON) {
                this.statusBar.setForeground(Color.RED);
                this.statusBar.setText("'X' Won! Click to play again.");
            } else if (this.currentState == State.NOUGHT_WON) {
                this.statusBar.setForeground(Color.RED);
                this.statusBar.setText("'O' Won! Click to play again.");
            }
        }

        public static void main(String[] args) {
            // Show welcome message and game level selection
            showWelcomeMessage();

            // Create the game frame
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Tic Tac Toe");
                frame.setContentPane(new TicTacToe());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }

        /**
         * Displays a welcome message and asks the user to select a game level.
         */
        private static void showWelcomeMessage() {
            // Welcome popup
            JOptionPane.showMessageDialog(null, "Welcome to Tic Tac Toe!", "Welcome", JOptionPane.INFORMATION_MESSAGE);

            // Level selection
            String[] levels = {"Easy", "Medium", "Hard"};
            gameLevel = (String) JOptionPane.showInputDialog(
                    null,
                    "Select your game level:",
                    "Game Level",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    levels,
                    levels[0]
            );

            // If no level is selected, set a default
            if (gameLevel == null) {
                gameLevel = "Easy";
            }
        }
    }
