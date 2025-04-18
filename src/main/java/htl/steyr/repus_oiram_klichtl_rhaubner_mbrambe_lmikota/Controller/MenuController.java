package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public Slider volumeSlider;
    @FXML
    public ImageView volumeIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicPlayer musicPlayer = MusicPlayer.getInstance();

        volumeSlider.setMin(0.0);    /* completely shut */
        volumeSlider.setMax(1.0);    /* maximum volume */
        volumeSlider.setValue(0.6);  /* start volume (60%) */

        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            float volumePercent = newVal.floatValue();
            float dB = volumeToDecibels(volumePercent);
            musicPlayer.setVolume(dB);

            if (newVal.equals(0.0)) {
                volumeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/mute.png"))));
            } else {
                volumeIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/medium-volume.png"))));
            }
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