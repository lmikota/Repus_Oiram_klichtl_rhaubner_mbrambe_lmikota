package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogs;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application.AppTestLmikota;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Story_Scene_Controller implements Initializable {

    @FXML
    public Button returnButton;
    @FXML
    public Button beginJourney;
    @FXML
    public Button nextDialog;
    @FXML
    public Text dialogText;

    public StoryDialogs storyDialogs;
    public int count = 0;
    public int selectedLevelID = 1;

    /* to load the controller out of the fxml */
    public Story_Scene_Controller() {}

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

            if (count < storyDialogs.getDialogs().size()) {
                /* Get the full dialog text for the current index */
                String fullText = storyDialogs.getDialogs().get(count).getDialog();

                /* Clear the text field before starting the animation */
                dialogText.setText("");

                /* Create a Timeline animation to display text gradually */
                Timeline timeline = new Timeline();

                /* Loop through each character in the dialog text */
                for (int i = 0; i < fullText.length(); i++) {
                    final int index = i; // Store the current character index
                    /* Add a KeyFrame that updates the text at regular intervals */
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(75 * i), e -> {
                        /* Set the text to show characters up to the current index */
                        dialogText.setText(fullText.substring(0, index + 1));
                    }));
                }
                /* Start the animation */
                timeline.play();
            }
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
    public void onReturnButtonClicked() {
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

    public void onNextDialogButtonClicked() {
        if (count < storyDialogs.getDialogs().size() - 1) {
            count++;
            showDialog();
        }
    }
}