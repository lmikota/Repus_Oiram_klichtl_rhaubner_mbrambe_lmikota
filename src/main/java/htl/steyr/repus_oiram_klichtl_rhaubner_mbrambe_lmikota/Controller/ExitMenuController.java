package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application.GameplayApplication;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application.StartApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitMenuController {

    @FXML
    public Text exitWarningText;

    public void exitLevelButtonClicked(ActionEvent actionEvent) {
        if (exitWarningText.isVisible()) {
            closeCurrentWindow(actionEvent);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/start_menu-view.fxml"));
                Parent newRoot = loader.load();
                Scene scene =  GameplayApplication.gameplayScene;
                scene.setRoot(newRoot);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        exitWarningText.setVisible(true);
    }

    private void closeCurrentWindow(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentWindow.close();
    }


}
