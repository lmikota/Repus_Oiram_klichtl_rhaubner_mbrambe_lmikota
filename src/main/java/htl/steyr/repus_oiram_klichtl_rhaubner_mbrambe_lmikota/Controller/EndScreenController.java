package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EndScreenController implements Initializable {

    @FXML
    private Text resultText;
    @FXML
    private AnchorPane endScreenAnchorPane;

    /**
     * In this Methode, the resultText gets displayed depending on the levelWon status of the Game.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        endScreenAnchorPane.toFront();
        if (GameplayApplication.levelWon) {
            resultText.setText("Congratulations you won!");
        } else {
            resultText.setText("You lost, try again");
        }
    }

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

    /**
     * In this Methode, StartMenu gets loaded when to user pressed the button.
     * @param actionEvent
     */
    @FXML
    public void onStartMenuButtonClicked(ActionEvent actionEvent) {
        closeCurrentWindow(actionEvent);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/start_menu-view.fxml"));
            Parent newRoot = loader.load();
            Scene scene = GameplayApplication.gameplayScene;
            scene.setRoot(newRoot);
        } catch (IOException e) {
            System.out.println("Error while loading the start-menu:" + e.getMessage());
        }
    }

    /* ---------------------------------------------- Visual Handling ----------------------------------------------  */

    /**
     * In this Methode, the current window get closed.
     * @param actionEvent
     */
    @FXML
    private void closeCurrentWindow(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentWindow.close();
    }
}