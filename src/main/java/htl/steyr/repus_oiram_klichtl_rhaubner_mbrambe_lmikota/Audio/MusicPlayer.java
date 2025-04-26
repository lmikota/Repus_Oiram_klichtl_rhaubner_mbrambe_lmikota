package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.Objects;

public class MusicPlayer {

    private static MusicPlayer instance;

    private Clip musicClip;
    private Clip soundClip;

    private FloatControl volumeControl;
    private float currentVolume = -10.0f;

    /* ----------------------------------------------- Music Controls ----------------------------------------------- */

    public void playMusic(String filepath) {
        try {
            stopMusic();
            URL soundUrl = Objects.requireNonNull(getClass().getResource(filepath));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioInputStream);

            volumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(currentVolume);

            musicClip.start();
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void playSound(String filepath) {
        try {
            if (soundClip != null && soundClip.isRunning()) {
                soundClip.stop();
            }
            URL soundUrl = Objects.requireNonNull(getClass().getResource(filepath));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);

            FloatControl soundVolumeControl = (FloatControl) soundClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundVolumeControl.setValue(currentVolume);

            soundClip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public void setVolume(float volume) {
        currentVolume = volume;
        if (volumeControl != null) {
            volumeControl.setValue(currentVolume);
        }
    }

    /* ------------------------------------------------- Singleton -------------------------------------------------- */

    public static MusicPlayer getInstance() {
        if (instance == null) {
            instance = new MusicPlayer();
        }
        return instance;
    }
}