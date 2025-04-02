package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ExitMenuController {

    @FXML
    public Text exitWarningText;

    public void exitLevelButtonClicked(ActionEvent actionEvent) {
        if (exitWarningText.isVisible()) {
            closeCurrentWindow(actionEvent);
        }
        exitWarningText.setVisible(true);
    }

    private void closeCurrentWindow(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentWindow.close();
    }
}
