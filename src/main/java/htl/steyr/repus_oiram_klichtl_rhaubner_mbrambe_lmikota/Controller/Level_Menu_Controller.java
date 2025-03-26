package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescription;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescriptionsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUser;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUserReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Level_Menu_Controller implements Initializable {

    @FXML
    public Button returnButton;
    @FXML
    public Button levelStartButton;
    @FXML
    public GridPane LevelDescriptionGrid;

    @FXML
    public ImageView Level1_Icon;
    @FXML
    public ImageView Level2_Icon;
    @FXML
    public ImageView Level3_Icon;
    @FXML
    public ImageView Level4_Icon;
    @FXML
    public ImageView Level5_Icon;
    @FXML
    public ImageView Level6_Icon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalUserReader localUserReader = new LocalUserReader();
        try {
            LocalUser localUser = localUserReader.readLocalUsersUnlockedLevels();

            if (localUser != null) {

                if ("locked".equals(localUser.getLevel_2())) {
                    disableLevelIcon(Level2_Icon);
                }
                if ("locked".equals(localUser.getLevel_3())) {
                    disableLevelIcon(Level3_Icon);
                }
                if ("locked".equals(localUser.getLevel_4())) {
                    disableLevelIcon(Level4_Icon);
                }
                if ("locked".equals(localUser.getLevel_5())) {
                    disableLevelIcon(Level5_Icon);
                }
                if ("locked".equals(localUser.getLevel_6())) {
                    disableLevelIcon(Level6_Icon);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLevelStartButtonClicked(ActionEvent actionEvent) {

    }

    public void onLevelSelected(MouseEvent mouseEvent) throws IOException {
        clearFromSelectionClass();
        ImageView selectedLevel = (ImageView) mouseEvent.getSource();
        selectedLevel.getStyleClass().add("selected-image-view");

        LevelDescriptionsReader reader = new LevelDescriptionsReader(getLevelIdByImageView(selectedLevel));
        LevelDescription levelDescription = reader.readLevelDescription();

        if (levelDescription != null) {
            LevelDescriptionGrid.add(new Text("Name"), 0, 0);
            LevelDescriptionGrid.add(new Text(levelDescription.getName()), 1, 0);

            LevelDescriptionGrid.add(new Text("Description"), 0, 1);
            LevelDescriptionGrid.add(new Text(levelDescription.getDescription()), 1, 1);

            LevelDescriptionGrid.add(new Text("Difficulty"), 0, 2);
            LevelDescriptionGrid.add(new Text(levelDescription.getDifficulty()), 1, 2);
        }
    }

    private void clearFromSelectionClass() {
        Level1_Icon.getStyleClass().remove("selected-image-view");
        Level2_Icon.getStyleClass().remove("selected-image-view");
        Level3_Icon.getStyleClass().remove("selected-image-view");
        Level4_Icon.getStyleClass().remove("selected-image-view");
        Level5_Icon.getStyleClass().remove("selected-image-view");
        Level6_Icon.getStyleClass().remove("selected-image-view");
        LevelDescriptionGrid.getChildren().clear();
    }

    private int getLevelIdByImageView(ImageView levelImageView) {
        if (levelImageView == Level1_Icon) {
            return 1;
        } else if (levelImageView == Level2_Icon) {
            return 2;
        } else if (levelImageView == Level3_Icon) {
            return 3;
        } else if (levelImageView == Level4_Icon) {
            return 4;
        } else if (levelImageView == Level5_Icon) {
            return 5;
        } else if (levelImageView == Level6_Icon) {
            return 6;
        } else {
            return -1;
        }
    }

    public void disableLevelIcon(ImageView icon) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        icon.setEffect(colorAdjust);
        icon.setDisable(true);
        icon.setOnMouseClicked(null);
    }

    @FXML
    public void onReturnButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/start_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = returnButton.getScene();
            scene.setRoot(newRoot);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
