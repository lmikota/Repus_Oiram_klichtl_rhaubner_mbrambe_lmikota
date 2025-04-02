package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescription;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescriptionsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUser;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUserReader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.Arrays;
import java.util.List;
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

    public int selectedLevel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalUserReader localUserReader = new LocalUserReader();
        try {
            LocalUser localUser = localUserReader.readLocalUsersUnlockedLevels();

            /* look if the user has unlocked the levels */
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
        if (selectedLevel > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/story_scene-view.fxml"));
                Parent newRoot = loader.load();

                Story_Scene_Controller storySceneController = loader.getController();
                storySceneController.setSelectedLevelID(getSelectedLevel());

                Scene scene = returnButton.getScene();
                scene.setRoot(newRoot);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No Level selected!");
        }
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
        /* Node is a base class for the graphical elements */
        List<Node> icons = Arrays.asList(Level1_Icon, Level2_Icon, Level3_Icon, Level4_Icon, Level5_Icon, Level6_Icon);

        for (Node icon : icons) {
            /* If Node is not used, the style could not be changed */
            icon.getStyleClass().remove("selected-image-view");
        }
        LevelDescriptionGrid.getChildren().clear();
    }

    private int getLevelIdByImageView(ImageView levelImageView) {
        if (levelImageView == Level1_Icon) {
            setSelectedLevel(1);
            return getSelectedLevel();
        } else if (levelImageView == Level2_Icon) {
            setSelectedLevel(2);
            return getSelectedLevel();
        } else if (levelImageView == Level3_Icon) {
            setSelectedLevel(3);
            return getSelectedLevel();
        } else if (levelImageView == Level4_Icon) {
            setSelectedLevel(4);
            return getSelectedLevel();
        } else if (levelImageView == Level5_Icon) {
            setSelectedLevel(5);
            return getSelectedLevel();
        } else if (levelImageView == Level6_Icon) {
            setSelectedLevel(6);
            return getSelectedLevel();
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

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}
