package TicTacToe;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Pastikan game dijalankan di thread GUI yang tepat
        SwingUtilities.invokeLater(() -> {
            System.out.println("Playing Tic Tac Toe");

            // Membuat frame untuk aplikasi Tic Tac Toe
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TicTacToe gamePanel = new TicTacToe(); // Panel permainan TicTacToe
            frame.setContentPane(gamePanel); // Set panel TicTacToe sebagai konten frame
            frame.pack(); // Menyesuaikan ukuran frame
            frame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar
            frame.setVisible(true); // Menampilkan frame

            // Inisialisasi dan putar efek suara
            SoundTicTacToe soundEffect = new SoundTicTacToe("SoundEffect.wav");
            soundEffect.play(); // Mainkan suara

            // Simulasikan game selama 5 detik
            try {
                Thread.sleep(5000); // Simulasikan game selama 5 detik
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Hentikan suara setelah 5 detik
            soundEffect.stop();
        });
    }
}
