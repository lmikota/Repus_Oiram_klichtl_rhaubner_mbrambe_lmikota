package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogs;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application.AppTestLmikota;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Story_Scene_Controller implements Initializable {

    @FXML
    public Button returnButton;
    @FXML
    public Button beginJourney;
    public Button nextDialog;
    @FXML
    private Text dialogText;

    private StoryDialogs storyDialogs;
    private int count = 0;
    private int selectedLevelID = 1;

    /* to load the controller out of the fxml */
    public Story_Scene_Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        StoryDialogsReader storyDialogsReader = new StoryDialogsReader();
        storyDialogsReader.setSelectedLevelID(selectedLevelID);

        try {
            storyDialogs = storyDialogsReader.readStoryDialogs();

            showDialog();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("StoryDialogs.json could not be found!");
        }
    }

    private void showDialog() {
        if (count <= storyDialogs.getDialogs().size()) {
            dialogText.setText(storyDialogs.getDialogs().get(count).getDialog());
        }
    }

    @FXML
    public void handleKeyPress(KeyEvent keyEvent) {
        /**
         * @TO-Do:
         * irgendwann fertig machen
         */
//        KeyCode key = keyEvent.getCode();
//        System.out.println("Key pressed: " + key);
//        if (key == KeyCode.SPACE) {
//            if (count < storyDialogs.getDialogs().size() - 1) {
//                count++;
//                showDialog();
//            }
//        }
    }

    @FXML
    public void onReturnButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/level_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = returnButton.getScene();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onBeginJourneyButtonClicked(ActionEvent actionEvent) {
        AppTestLmikota appTestLmikota = new AppTestLmikota();
        if (getSelectedLevelID() > 0) {
            switch (getSelectedLevelID()) {
                case 1:
                    appTestLmikota.setSelectedLevel(1);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                case 2:
                    appTestLmikota.setSelectedLevel(2);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                case 3:
                    appTestLmikota.setSelectedLevel(3);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                case 4:
                    appTestLmikota.setSelectedLevel(4);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                case 5:
                    appTestLmikota.setSelectedLevel(5);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                case 6:
                    appTestLmikota.setSelectedLevel(6);
                    appTestLmikota.start(new Stage());
                    closeCurrentWindow(actionEvent);
                    break;
                default:
                    System.out.println("Unexpected Error while choosing Level");
                    break;
            }
        }
    }

    private void closeCurrentWindow(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentWindow.close();
    }

    public int getSelectedLevelID() {
        return selectedLevelID;
    }

    public void setSelectedLevelID(int selectedLevelID) {
        this.selectedLevelID = selectedLevelID;
    }

    public void onNextDialogButtonClicked(ActionEvent actionEvent) {
        if (count < storyDialogs.getDialogs().size() - 1) {
            count++;
            showDialog();
        }
    }
}