package TicTacToe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Playing Tic Tac Toe");

        // Initialize the sound effect with the sound file name
        // Replace "your_sound_file.wav" with the actual path to your sound file
        SoundTicTacToe soundEffect = new SoundTicTacToe("SoundEffect.wav");

        // Play the sound (loop continuously)
        soundEffect.play();

        // Simulate the game running (you can replace this with actual game logic)
        try {
            Thread.sleep(5000); // Play sound for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the sound after 5 seconds
        soundEffect.stop();
    }
}

