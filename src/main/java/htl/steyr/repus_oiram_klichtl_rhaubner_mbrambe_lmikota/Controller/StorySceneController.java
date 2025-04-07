package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogs;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.StoryDialogsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StorySceneController implements Initializable {

    @FXML
    public AnchorPane storySceneAnchorPane;
    @FXML
    public Text dialogText;
    @FXML
    public StoryDialogs storyDialogs;
    @FXML
    public HBox buttonRowHBOX;
    @FXML
    public Button returnButton;
    @FXML
    public Button beginJourney;
    @FXML
    public Button nextDialog;

    public int count;
    public int selectedLevelID;
    public Timeline currentTimeline;

    /* to load the controller out of the fxml */
    public StorySceneController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCount(0);
        applyLayoutBindings();
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

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------  */

    @FXML
    public void onReturnButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/level_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = returnButton.getScene();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onNextDialogButtonClicked() {
        if (getCount() < storyDialogs.getDialogs().size() - 1) {
            setCount(getCount() + 1);
            showDialog();
        } else {
            nextDialog.setDisable(true);
        }

    }

    @FXML
    public void onBeginJourneyButtonClicked(ActionEvent actionEvent) {
        GameplayApplication gameplayApplication = new GameplayApplication();
        if (getSelectedLevelID() > 0) {
            gameplayApplication.setSelectedLevel(getSelectedLevelID());
            gameplayApplication.start(new Stage());
            closeCurrentWindow(actionEvent);
        }
    }

    /* ---------------------------------------------- Visual Handling ----------------------------------------------  */

    @FXML
    public void applyLayoutBindings() {
        /* Positioning and Spacing for the button row HBOX */
        buttonRowHBOX.translateYProperty()
                .bind(storySceneAnchorPane.heightProperty().multiply(0.8));
        buttonRowHBOX.spacingProperty()
                .bind(storySceneAnchorPane.widthProperty().multiply(0.15));

        /* Place the dialogText around the middle of the screen */
        dialogText.translateYProperty()
                .bind(storySceneAnchorPane.heightProperty().multiply(0.12));
        dialogText.setStyle("-fx-font-size: 50px");
    }

    @FXML
    private void showDialog() {
        if (count < storyDialogs.getDialogs().size()) {
            /* Get the full dialog text for the current index */
            String fullText = storyDialogs.getDialogs().get(count).getDialog();

            /* Stop any running animation before starting a new one */
            if (currentTimeline != null) {
                currentTimeline.stop();
            }

            /* Clear the text field before starting the animation */
            dialogText.setText("");

            /* Create a Timeline animation to display text gradually */
            currentTimeline = new Timeline();

            /* Loop through each character in the dialog text */
            for (int i = 0; i < fullText.length(); i++) {
                final int index = i; // Store the current character index

                /* Add a KeyFrame that updates the text at regular intervals */
                currentTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(75 * i), e -> {
                    /* Set the text to show characters up to the current index */
                    dialogText.setText(fullText.substring(0, index + 1));
                }));
            }

            /* Start the animation */
            currentTimeline.play();
        }
    }

    @FXML
    private void closeCurrentWindow(ActionEvent actionEvent) {
        Stage currentWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        currentWindow.close();
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public int getSelectedLevelID() {
        return selectedLevelID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setupStory(int selectedLevelID) {
        this.selectedLevelID = selectedLevelID;
        StoryDialogsReader reader = new StoryDialogsReader();
        reader.setSelectedLevelID(selectedLevelID);
        try {
            this.storyDialogs = reader.readStoryDialogs();
            showDialog();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("StoryDialogs.json could not be found!");
        }
    }
}