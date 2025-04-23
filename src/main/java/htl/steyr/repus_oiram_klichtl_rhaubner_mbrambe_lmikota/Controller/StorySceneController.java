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
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StorySceneController implements Initializable {

    @FXML
    public Button returnButton;
    @FXML
    public Button beginJourneyButton;
    @FXML
    public Button nextDialogButton;
    @FXML
    public Text dialogText;
    @FXML
    public AnchorPane storySceneAnchorPane;
    @FXML
    public StoryDialogs storyDialogs;

    public int count = 0;
    public int selectedLevelID = 1;

    public Timeline currentTimeline;

    /* to load the controller out of the fxml */
    public StorySceneController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StoryDialogsReader storyDialogsReader = new StoryDialogsReader();
        storyDialogsReader.setSelectedLevelID(selectedLevelID);

        try {
            storyDialogs = storyDialogsReader.readStoryDialogs();

            showDialog();

            if (storyDialogs.getDialogs().size() <= 1) {
                nextDialogButton.setDisable(true);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("StoryDialogs.json could not be found!");
        }
        applyLayoutBindings();
    }

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

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
        if (nextDialogButton.isDisabled()) return;

        count++;

        if (count < storyDialogs.getDialogs().size()) {
            dialogText.setText("");
            showDialog();
        }

        if (count >= storyDialogs.getDialogs().size() - 1) {
            nextDialogButton.setDisable(true);
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
    private void showDialog() {
        /* Stop the current animation if one is running */
        if (currentTimeline != null) {
            currentTimeline.stop();
        }

        /* Set the full dialog text initially (serves as fallback) */
        dialogText.setText(storyDialogs.getDialogs().get(count).getDialog());

        if (count < storyDialogs.getDialogs().size()) {
            /* Get the full dialog text for the current index */
            String fullText = storyDialogs.getDialogs().get(count).getDialog();

            /* Clear the text field before starting animation */
            dialogText.setText("");

            /* Create new Timeline for character-by-character animation */
            currentTimeline = new Timeline();

            /* Build animation by adding keyframes for each character */
            for (int i = 0; i < fullText.length(); i++) {
                /* Store current character index */
                final int index = i;
                currentTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(75 * i), e -> {
                            /* Update text to show characters up to current index */
                            dialogText.setText(fullText.substring(0, index + 1));
                        })
                );
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

    @FXML
    private void applyLayoutBindings() {

        /* Centering the Next Dialog button horizontally */
        nextDialogButton.translateXProperty().bind(
                storySceneAnchorPane.widthProperty()
                        .subtract(nextDialogButton.prefWidth(-1))
                        .divide(2)
        );

        /* Positioning of the Dialog-Text */
        dialogText.translateXProperty().bind(
                storySceneAnchorPane.widthProperty()
                        .subtract(dialogText.prefWidth(-1))
                        .divide(2)
        );

        dialogText.translateYProperty().bind(
                storySceneAnchorPane.heightProperty()
                        .subtract(dialogText.prefHeight(-1))
                        .divide(3)
        );

        /* Return-Button: Bottom Left */
        returnButton.translateYProperty().bind(
                storySceneAnchorPane.heightProperty().multiply(0.80)
        );
        returnButton.translateXProperty().bind(
                storySceneAnchorPane.widthProperty().multiply(0.05)
        );

        /* Next-Dialog-Button: Bottom Center */
        nextDialogButton.translateYProperty().bind(
                storySceneAnchorPane.heightProperty().multiply(0.80)
        );

        /* Begin-Journey-Button: Bottom Right */
        beginJourneyButton.translateYProperty().bind(
                storySceneAnchorPane.heightProperty().multiply(0.80)
        );
        beginJourneyButton.translateXProperty().bind(
                storySceneAnchorPane.widthProperty()
                        .subtract(beginJourneyButton.prefWidth(-1))
                        .multiply(0.95)
        );
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public int getSelectedLevelID() {
        return selectedLevelID;
    }

    public void setSelectedLevelID(int selectedLevelID) {
        this.selectedLevelID = selectedLevelID;
    }
}