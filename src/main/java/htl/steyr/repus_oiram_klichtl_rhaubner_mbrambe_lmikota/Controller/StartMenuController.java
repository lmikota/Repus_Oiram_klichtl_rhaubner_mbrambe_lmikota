package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class StartMenuController {

    @FXML
    public Button startButton;
    @FXML
    private StackPane rootPane;
    @FXML
    private Parent menuOverlay;

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

    /**
     * In this Methode, the level-menu gets loaded in the current scene.
     */
    @FXML
    public void onStartButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/level_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = startButton.getScene();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/CSS/style.css")).toExternalForm());
            scene.setRoot(newRoot);

        } catch (IOException e) {
            System.err.println("Error loading level menu: " + e.getMessage());
        }
    }

    /**
     * In this Methode, the volumeSettings get displayed on the current scene.
     */
    @FXML
    public void onMenuButtonClicked() {
        try {
            if (menuOverlay == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/volume_settings-view.fxml"));
                menuOverlay = loader.load();

                VolumeSettingsController menuController = loader.getController();
                menuController.setMenuOverlay(menuOverlay);

                rootPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (menuOverlay.isVisible() && !menuOverlay.getBoundsInParent().contains(e.getX(), e.getY())) {
                        menuOverlay.setVisible(false);
                    }
                });
            }

            if (!rootPane.getChildren().contains(menuOverlay)) {
                rootPane.getChildren().add(menuOverlay);
            }
            menuOverlay.setVisible(true);

        } catch (IOException e) {
            System.out.println("Error while loading the menu:" + e.getMessage());
        }
    }

    /**
     * In this Methode, the music gets stopped and the Platform is exited closing the game.
     */
    @FXML
    public void onExitButtonClicked() {
        MusicPlayer.getInstance().stopMusic();
        Platform.exit();
    }
}