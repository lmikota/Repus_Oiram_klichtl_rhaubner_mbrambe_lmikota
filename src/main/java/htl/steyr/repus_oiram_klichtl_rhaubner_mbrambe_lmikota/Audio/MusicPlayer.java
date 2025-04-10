package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    private Clip clip;

    public void playMusic(String filepath) {
        try {
            File audioFile = new File(filepath);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("The audio file format is not supported: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("An I/O error occurred while trying to read the audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            throw new RuntimeException("The audio line is unavailable: " + e.getMessage());
        }
    }

    public void playSound(String filepath) {
        try {
            File audioFile = new File(filepath);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException("The audio file format is not supported: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("An I/O error occurred while trying to read the audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            throw new RuntimeException("The audio line is unavailable: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}