package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;

public class MusicPlayer {

    private static MusicPlayer instance;

    private Clip clip;
    private FloatControl volumeControl;
    private float currentVolume = -10.0f;

    private MusicPlayer() {
        /* private constructor, helps to make it a singleton */
    }

    public void playMusic(String filepath) {
        try {
            URL soundUrl = Objects.requireNonNull(getClass().getResource(filepath));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(currentVolume);

            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abspielen: " + e.getMessage());
        }
    }

    public void playSound(String filepath) {
        try {
            URL soundUrl = Objects.requireNonNull(getClass().getResource(filepath));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(currentVolume);

            clip.start();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abspielen: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public void setVolume(float volume) {
        currentVolume = volume;
        if (volumeControl != null) {
            volumeControl.setValue(currentVolume);
        }
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
}