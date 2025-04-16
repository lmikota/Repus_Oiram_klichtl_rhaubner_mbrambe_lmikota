package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public Slider audioSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        audioSlider.setMin(0.0);    /* completely shut */
        audioSlider.setMax(1.0);    /* maximum volume */
        audioSlider.setValue(0.6);  /* start volume (60%) */

        audioSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float volumePercent = newVal.floatValue();
            float dB = volumeToDecibels(volumePercent);
            musicPlayer.setVolume(dB);
        });
    }

    /* methode to help convert from a linear-percent-scale to dB */
    private float volumeToDecibels(float volumePercent) {
        if (volumePercent == 0.0f) {
            return -80.0f; /* completely shut */
        }
        /* dB = 20 * log10(volume) */
        return (float) (20.0 * Math.log10(volumePercent));
    }

}