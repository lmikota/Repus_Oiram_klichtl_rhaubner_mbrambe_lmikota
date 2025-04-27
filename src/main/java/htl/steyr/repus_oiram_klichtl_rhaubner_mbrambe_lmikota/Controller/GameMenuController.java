package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuController {

    @FXML
    private StackPane volumeOverlayPane;
    @FXML
    private Parent menuOverlay;
    @FXML
    private Parent settingsOverlay;
    @FXML
    private GameplayApplication gameplayApplication;

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

    /**
     * In this Methode, the game is resumed from the Menu
     */
    @FXML
    public void onPlayButtonClicked() {
        if (menuOverlay != null) {
            menuOverlay.setVisible(false);
        }
        getGameplayApplication().resumeTimer();
    }

    /**
     * In this Methode, the Level is restarted from the literal beginning.
     *      * No checkpoints.
     * @param actionEvent
     */
    @FXML
    public void onRestartButtonClicked(ActionEvent actionEvent) {
        GameplayApplication gameplayApplication = new GameplayApplication();

            gameplayApplication.start(new Stage());
            closeCurrentWindow(actionEvent);
    }

    /**
     * In this Methode, the option (sound-) Menu gets displayed.
     */
    @FXML
    public void onOptionButtonClicked() {
        try {
            if (volumeOverlayPane == null)
                throw new IllegalStateException("Pane ned da");

            if (settingsOverlay == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/volume_settings-view.fxml"));
                settingsOverlay = loader.load();

                VolumeSettingsController settingsCtrl = loader.getController();
                settingsCtrl.setMenuOverlay(settingsOverlay);

                volumeOverlayPane.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                    if (settingsOverlay.isVisible()
                            && !settingsOverlay.getBoundsInParent().contains(e.getX(), e.getY())) {
                        settingsOverlay.setVisible(false);
                    }
                });
            }

            if (!volumeOverlayPane.getChildren().contains(settingsOverlay)) {
                volumeOverlayPane.getChildren().add(settingsOverlay);
            }
            settingsOverlay.setVisible(true);

        } catch (IOException | IllegalStateException e) {
            System.out.println("Error while loading the option-menu:" + e.getMessage());
        }
    }

    /**
     * In this Methode, the user gets sent back to the startMenu.
     * No progress saved.
     */
    @FXML
    public void onQuitButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/start_menu-view.fxml"));
            Parent newRoot = loader.load();
            Scene scene = GameplayApplication.gameplayScene;
            scene.setRoot(newRoot);
        } catch (IOException e) {
            System.out.println("Error while loading the start-menu: " + e.getMessage());
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

    /**
     * In this Method, the menuOverlay is set.
     * @param menuOverlay
     */
    @FXML
    public void setMenuOverlay(Parent menuOverlay) {
        this.menuOverlay = menuOverlay;
    }

    public GameplayApplication getGameplayApplication() {
        return gameplayApplication;
    }

    public void setGameplayApplication(GameplayApplication gameplayApplication) {
        this.gameplayApplication = gameplayApplication;
    }
}