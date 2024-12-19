package Resource.src.TicTacToe;

import javax.sound.sampled.*;
import java.io.InputStream;

public class SoundTicTacToe {
    private Clip clip;

    // Constructor to load the sound from a file
    public SoundTicTacToe(String soundFileName) {
        try {
            // Use getResourceAsStream to load files inside the src folder
            InputStream audioSrc = getClass().getClassLoader().getResourceAsStream(soundFileName);
            if (audioSrc == null) {
                throw new Exception("Sound file not found: " + soundFileName);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioSrc);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to start audio looping
    public void play() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Method to play audio once
    public void playOnce() {
        if (clip != null) {
            clip.setFramePosition(0); // Reset the clip to start
            clip.start(); // Play the sound once
        }
    }

    // Method to stop audio
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
