package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;

import com.google.gson.Gson;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescription;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LevelDescriptionsReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUser;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUserReader;
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

import java.io.FileWriter;
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
    public AnchorPane levelMenuAnchorPane;
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
    public GridPane levelDescriptionGrid;

    public int selectedLevel;

    public Map<ImageView, Integer> levelMap;
    public static LocalUser localUser;
    private final Gson gson = new Gson();

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

        try {
            localUser = LocalUserReader.readLocalUser();

            /* look if the user has unlocked the levels */
            if (localUser != null) {
                for (int i = 1; i < 7; i++) {
                    if (!localUser.getHighscores().get(i).equals("no")) {
                        System.out.println(i);
                        System.out.println(localUser.getHighscores().get(i));
                        try (FileWriter wr = new FileWriter("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/LocalUser.json")) {
                            localUser.updateLevelLock(i);
                            gson.toJson(localUser, wr);
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }

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
            System.out.println("Error while reading the local-user-data: " + e.getMessage());
        }
        applyLayoutBindings();
    }

    /* ---------------------------------------------- Buttons Clicked ----------------------------------------------- */

    @FXML
    public void onLevelStartButtonClicked() {
        if (selectedLevel > 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/story_scene-view.fxml"));
                Parent newRoot = loader.load();

                StorySceneController storySceneController = loader.getController();
                storySceneController.setSelectedLevelID(getSelectedLevel());

                Scene scene = returnButton.getScene();
                scene.setRoot(newRoot);
            } catch (IOException e) {
                System.out.println("Error while loading the level: " + e.getMessage());
            }
        } else {
            System.out.println("No Level selected!");
        }
    }

    @FXML
    public void onLevelSelected(MouseEvent mouseEvent) throws IOException {
        clearFromSelectionClass();
        ImageView selectedLevel = (ImageView) mouseEvent.getSource();

        /* the selected Level-Icon gets a different css-style than the others */
        selectedLevel.getStyleClass().add("selected-image-view");

        LevelDescriptionsReader reader = new LevelDescriptionsReader(getLevelIdByImageView(selectedLevel));
        LevelDescription levelDescription = reader.readLevelDescription();

        if (levelDescription != null) {
            levelDescriptionGrid.add(new Text("Name"), 0, 0);
            levelDescriptionGrid.add(new Text(levelDescription.getName()), 1, 0);

            levelDescriptionGrid.add(new Text("Description"), 0, 1);
            levelDescriptionGrid.add(new Text(levelDescription.getDescription()), 1, 1);

            levelDescriptionGrid.add(new Text("Difficulty"), 0, 2);
            levelDescriptionGrid.add(new Text(levelDescription.getDifficulty()), 1, 2);

            levelDescriptionGrid.add(new Text("Record Time"), 0, 3);
            levelDescriptionGrid.add(new Text(localUser.getHighscores().get(levelDescription.getId())), 1, 3);
        }
    }

    @FXML
    public void onReturnButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/start_menu-view.fxml"));
            Parent newRoot = loader.load();

            Scene scene = returnButton.getScene();
            scene.setRoot(newRoot);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* ---------------------------------------------- Visual Handling ----------------------------------------------  */

    @FXML
    private void clearFromSelectionClass() {
        /* Node is a base class for the graphical elements */
        List<Node> icons = Arrays.asList(Level1_Icon, Level2_Icon, Level3_Icon, Level4_Icon, Level5_Icon, Level6_Icon);

        for (Node icon : icons) {
            /* If Node is not used, the style could not be changed */
            icon.getStyleClass().remove("selected-image-view");
        }
        /* the Grid gets cleared */
        levelDescriptionGrid.getChildren().clear();
    }

    @FXML
    private int getLevelIdByImageView(ImageView levelImageView) {
        Integer level = levelMap.get(levelImageView);
        if (level != null) {
            setSelectedLevel(level);
            return level;
        }
        return -1;
    }

    @FXML
    public void disableLevelIcon(ImageView icon) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        icon.setEffect(colorAdjust);
        icon.setDisable(true);
        icon.setOnMouseClicked(null);
        icon.setStyle("-fx-opacity: 0.75");
    }

    @FXML
    public void applyLayoutBindings() {

        /* Positioning and spacing for the first row of level icons */
        firstRowIconsHBox.translateYProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.25));
        firstRowIconsHBox.spacingProperty()
                .bind(levelMenuAnchorPane.widthProperty().multiply(0.025));

        /* Positioning for the second row of level icons */
        secondLevelIconHBox.translateYProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.45));

        /* List of level icons for uniform sizing */
        List<ImageView> levelIcons = new ArrayList<>(List.of(
                Level1_Icon, Level2_Icon, Level3_Icon, Level4_Icon, Level5_Icon
        ));

        /* Binding height and width for level icons */
        for (ImageView icon : levelIcons) {
            icon.fitHeightProperty().bind(levelMenuAnchorPane.heightProperty().multiply(0.15));
            icon.fitWidthProperty().bind(levelMenuAnchorPane.widthProperty().multiply(0.10));
        }

        /* Special sizing for Level 6 icon */
        Level6_Icon.fitHeightProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.15));
        Level6_Icon.fitWidthProperty()
                .bind(levelMenuAnchorPane.widthProperty().multiply(0.60));

        /* Centering the level overview text horizontally */
        levelOverviewText.translateXProperty().bind(
                levelMenuAnchorPane.widthProperty()
                        .subtract(levelOverviewText.prefWidth(-1))
                        .divide(2)
        );

        /* Positioning level overview text with a fixed offset from the top */
        levelOverviewText.translateYProperty().bind(
                levelMenuAnchorPane.heightProperty().multiply(0.2)
                        .subtract(levelOverviewText.getLayoutBounds().getHeight() / 2)
        );

        /* Positioning and spacing for button grid */
        buttonGridHBox.translateYProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.65));
        buttonGridHBox.spacingProperty()
                .bind(levelMenuAnchorPane.widthProperty().multiply(0.025));

        /* Resizing button grid */
        buttonGridHBox.prefHeightProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.15));

        /* Spacing adjustment for buttons */
        buttonHBox.spacingProperty()
                .bind(levelMenuAnchorPane.heightProperty().multiply(0.02));

        levelDescriptionGrid.prefWidthProperty().bind(levelMenuAnchorPane.widthProperty().multiply(0.475));
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}