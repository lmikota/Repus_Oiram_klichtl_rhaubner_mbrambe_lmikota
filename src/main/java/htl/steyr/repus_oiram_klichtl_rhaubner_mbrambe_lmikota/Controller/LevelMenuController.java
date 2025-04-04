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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LevelMenuController implements Initializable {

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

    @FXML
    public AnchorPane level_menu_AnchorPane;
    @FXML
    public HBox firstRowIconsHBox;
    @FXML
    public HBox secondLevelIconHBox;
    @FXML
    public HBox buttonGridHBox;
    @FXML
    public VBox buttonHBox;
    @FXML
    public Button returnButton;
    @FXML
    public Button levelStartButton;
    @FXML
    public Text levelOverviewText;
    @FXML
    public GridPane LevelDescriptionGrid;

    public int selectedLevel;

    private Map<ImageView, Integer> levelMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* HashMap to store all Icons and the ID */
        levelMap = new HashMap<>();
        levelMap.put(Level1_Icon, 1);
        levelMap.put(Level2_Icon, 2);
        levelMap.put(Level3_Icon, 3);
        levelMap.put(Level4_Icon, 4);
        levelMap.put(Level5_Icon, 5);
        levelMap.put(Level6_Icon, 6);

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
        setLayout();
    }

    @FXML
    public void onLevelStartButtonClicked(ActionEvent actionEvent) {
        if (selectedLevel > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/story_scene-view.fxml"));
                Parent newRoot = loader.load();

                StorySceneController storySceneController = loader.getController();
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

        /* the selected Level-Icon gets a different css-style than the others */
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
        /* the Grid gets cleared */
        LevelDescriptionGrid.getChildren().clear();
    }

    private int getLevelIdByImageView(ImageView levelImageView) {
        Integer level = levelMap.get(levelImageView);
        if (level != null) {
            setSelectedLevel(level);
            return level;
        }
        return -1;
    }

    public void disableLevelIcon(ImageView icon) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        icon.setEffect(colorAdjust);
        icon.setDisable(true);
        icon.setOnMouseClicked(null);
        icon.setStyle("-fx-opacity: 0.75");
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

    public void setLayout() {

        /* Positioning and spacing for the first row of level icons */
        firstRowIconsHBox.translateYProperty()
                .bind(level_menu_AnchorPane.heightProperty().multiply(0.25));
        firstRowIconsHBox.spacingProperty()
                .bind(level_menu_AnchorPane.widthProperty().multiply(0.025));

        /* Positioning for the second row of level icons */
        secondLevelIconHBox.translateYProperty()
                .bind(level_menu_AnchorPane.heightProperty().multiply(0.45));

        /* List of level icons for uniform sizing */
        List<ImageView> levelIcons = new ArrayList<>(List.of(
                Level1_Icon, Level2_Icon, Level3_Icon, Level4_Icon, Level5_Icon
        ));

        /* Binding height and width for level icons */
        for (ImageView icon : levelIcons) {
            icon.fitHeightProperty().bind(level_menu_AnchorPane.heightProperty().multiply(0.15));
            icon.fitWidthProperty().bind(level_menu_AnchorPane.widthProperty().multiply(0.10));
        }

        /* Special sizing for Level 6 icon */
        Level6_Icon.fitHeightProperty()
                .bind(level_menu_AnchorPane.heightProperty().multiply(0.15));
        Level6_Icon.fitWidthProperty()
                .bind(level_menu_AnchorPane.widthProperty().multiply(0.60));

        /* Centering the level overview text horizontally */
        levelOverviewText.translateXProperty().bind(
                level_menu_AnchorPane.widthProperty()
                        .subtract(levelOverviewText.prefWidth(-1))
                        .divide(2)
        );

        /* Positioning level overview text with a fixed offset from the top */
        levelOverviewText.translateYProperty().bind(
                level_menu_AnchorPane.heightProperty().multiply(0.2)
                        .subtract(levelOverviewText.getLayoutBounds().getHeight() / 2)
        );

        /* Positioning and spacing for button grid */
        buttonGridHBox.translateYProperty()
                .bind(level_menu_AnchorPane.heightProperty().multiply(0.65));
        buttonGridHBox.spacingProperty()
                .bind(level_menu_AnchorPane.widthProperty().multiply(0.05));

        /* Resizing button grid */
        buttonGridHBox.prefWidthProperty()
                .bind(level_menu_AnchorPane.widthProperty().multiply(0.8));
        buttonGridHBox.prefHeightProperty()
                .bind(level_menu_AnchorPane.heightProperty().multiply(0.15));

        /* Spacing adjustment for buttons */
        buttonHBox.spacingProperty().bind(level_menu_AnchorPane.heightProperty().multiply(0.02));
    }

    /* Getter and Setter */

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}