package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Start_Menu_Controller {

    @FXML
    public Button switch_to_Tutorial_Button;

    private int playerCount;

    public void switch_To_Tutorial(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/tutorial-view.fxml"));
            Parent root = null;
            root = loader.load();

            Tutorial_Controller tutorialController = loader.getController();

            Stage stage = (Stage) switch_to_Tutorial_Button.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();


        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*---------------------------------------------- Getter and Setter ----------------------------------------------*/

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }


}