package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;


import java.io.IOException;
import java.util.Objects;

public class StartMenuController {

    @FXML
    public Button startButton;

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

    @FXML
    public void onStartButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/level_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = startButton.getScene();
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/CSS/ui-style.css")).toExternalForm());
            scene.setRoot(newRoot);

        } catch (IOException e) {
            System.err.println("Error loading level menu: " + e.getMessage());
        }
    }

    @FXML
    public void onMenuButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/menu-view.fxml"));
            Parent menuRoot = loader.load();

            Scene menuScene = new Scene(menuRoot);

            /* Set up a new stage (window) for the menu */
            javafx.stage.Stage menuStage = new javafx.stage.Stage();
            menuStage.setTitle("Menu");
            menuStage.setScene(menuScene);
            menuStage.setResizable(false);

            /* Make the new stage modal and set its owner to the current window */
            javafx.stage.Window currentWindow = startButton.getScene().getWindow();
            menuStage.initOwner(currentWindow);
            menuStage.initModality(Modality.WINDOW_MODAL);

            menuStage.show();

        } catch (IOException e) {
            System.err.println("Error loading menu: " + e.getMessage());
        }
    }

    @FXML
    public void onExitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
        MusicPlayer.getInstance().stopMusic();
    }
}