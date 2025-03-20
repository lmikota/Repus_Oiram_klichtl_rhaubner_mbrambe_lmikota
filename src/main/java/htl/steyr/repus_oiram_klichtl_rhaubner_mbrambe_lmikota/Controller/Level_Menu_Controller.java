package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

public class Level_Menu_Controller {

    @FXML
    public Button returnButton;
    public Button levelStartButton;

    public javafx.scene.image.ImageView Level1_Icon;
    public javafx.scene.image.ImageView Level2_Icon;
    public javafx.scene.image.ImageView Level3_Icon;
    public javafx.scene.image.ImageView Level4_Icon;
    public javafx.scene.image.ImageView Level5_Icon;
    public javafx.scene.image.ImageView Level6_Icon;

    @FXML
    public void onReturnButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/start_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = returnButton.getScene();
            scene.setRoot(newRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onLevelStartButtonClicked(ActionEvent actionEvent) {

    }

    public void onLevelSelected(javafx.scene.input.MouseEvent mouseEvent) {
        clearFromSelectionClass();
        ImageView selectedLevel = (ImageView) mouseEvent.getSource();
        selectedLevel.getStyleClass().add("selected-image-view");
    }

    private void clearFromSelectionClass() {
        Level1_Icon.getStyleClass().remove("selected-image-view");
        Level2_Icon.getStyleClass().remove("selected-image-view");
        Level3_Icon.getStyleClass().remove("selected-image-view");
        Level4_Icon.getStyleClass().remove("selected-image-view");
        Level5_Icon.getStyleClass().remove("selected-image-view");
        Level6_Icon.getStyleClass().remove("selected-image-view");
    }

}