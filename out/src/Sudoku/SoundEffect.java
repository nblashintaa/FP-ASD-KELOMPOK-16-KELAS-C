package Sudoku;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundEffect {
    WRONG("SoundEffect.wav"),
    EXPLODE("SoundEffect.wav"),
    DIE("SoundEffect.wav");

    /** Nested enumeration for specifying volume */
    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    /** Each sound effect has its own clip, loaded with its own sound file. */
    private Clip clip;

    /** Private Constructor to construct each element of the enum with its own sound file. */
    private SoundEffect(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            if (url == null) {
                throw new IllegalArgumentException("Sound file not found: " + soundFileName);
            }
            System.out.println("Loading sound file: " + soundFileName);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file format: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading audio file: " + e.getMessage());
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Play or re-play the sound effect from the beginning, by rewinding. */
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip != null) {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the player if it is still running
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.start(); // Start playing
                System.out.println("Playing sound: " + this.name());
            } else {
                System.err.println("Clip is not initialized.");
            }
        } else {
            System.out.println("Sound is muted.");
        }
    }

    /** Optional static method to pre-load all the sound files. */
    static void initGame() {
        values(); // Calls the constructor for all the elements
    }
}