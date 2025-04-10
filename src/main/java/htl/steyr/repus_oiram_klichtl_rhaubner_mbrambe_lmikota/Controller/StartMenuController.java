package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

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

public class StartMenuController {

    @FXML
    public Button startButton;

    @FXML
    public void onStartButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/level_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = startButton.getScene();
            scene.getStylesheets().add(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/CSS/ui-style.css").toExternalForm());
            scene.setRoot(newRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onMenuButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/menu-view.fxml"));
            Parent menuRoot = loader.load();

            Scene menuScene = new Scene(menuRoot);

            javafx.stage.Stage menuStage = new javafx.stage.Stage();
            menuStage.setTitle("Menü");
            menuStage.setScene(menuScene);
            menuStage.setResizable(false);

            javafx.stage.Window currentWindow = startButton.getScene().getWindow();
            menuStage.initOwner(currentWindow);

            menuStage.initModality(Modality.WINDOW_MODAL);
            menuStage.initOwner(startButton.getScene().getWindow());

            menuStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onExitButtonClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}