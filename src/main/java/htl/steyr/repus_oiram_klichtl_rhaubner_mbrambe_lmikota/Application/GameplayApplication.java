package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application;

import com.google.gson.Gson;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller.GameMenuController;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller.LevelMenuController;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GameplayApplication extends Application {
    private static final int SCROLL_SPEED = 4;
    private final double GRAVITY = 0.5;
    private double offsetX = 0;
    private static int selectedLevel;
    public static Scene gameplayScene;
    private String time;
    private ImageView bg1;
    private ImageView bg2;
    private Pane root = new Pane();
    private StackPane overlayContainer;
    public static boolean levelWon = false;
    private boolean endScreenLoaded = false;

    private StackPane mainContainer;
    private Pane gameLayer;
    private StackPane overlayLayer;

    private Timeline timerTimeline;

    private int secondsSinceStart = 0;

    private static MapDataReader mapDataReader;

    @FXML
    public static Text timerDisplay = new Text();

    @FXML
    private final ImageView heart1 = new ImageView();
    @FXML
    private final ImageView heart2 = new ImageView();
    @FXML
    private final ImageView heart3 = new ImageView();

    private Player player;
    private SuperUmhang cape;
    private SuperBoots boots;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The start Method starts a Level.
     * It initializes the Tilemap, Player and enemeies
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */

    @Override
    public void start(Stage primaryStage) {
        try {
            mainContainer = new StackPane();
            gameLayer = new Pane();
            overlayLayer = new StackPane();
            mainContainer.getChildren().addAll(gameLayer, overlayLayer);
            gameplayScene = new Scene(mainContainer);

            mapDataReader = new MapDataReader();
            Tilemap tilemap = new Tilemap(mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
            setBg1(createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/LevelBackgrounds/Level_" + getSelectedLevel() + "_Background/Level_" + getSelectedLevel() + "-Background_1.png"));
            setBg2(createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/LevelBackgrounds/Level_" + getSelectedLevel() + "_Background/Level_" + getSelectedLevel() + "-Background_2.png"));
            startTimer();
            addToRoot(gameLayer, bg1);
            addToRoot(gameLayer, bg2);

            displayTimer();
            getBg2().setX(bg1.getImage().getWidth());
            addToRoot(gameLayer, tilemap.getTyleMapPane());

            gameplayScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/CSS/style.css")).toExternalForm());

            player = new Player(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/Character_Repus.png"))), tilemap.getTILE_SIZE(), tilemap.getTILE_SIZE(), this);
            addToRoot(gameLayer, player.getPlayerImage());



            for (String enemyType : mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().keySet()) {
                switch (enemyType) {
                    case "floorEnemies":
                        for (int i = 1; i <= mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("floorEnemies").size(); i++) {
                            FloorEnemy floorEnemy = new FloorEnemy(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/flyEnemy.png"))), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("floorEnemies").get(i)[2], player,
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getMapData(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("floorEnemies").get(i)[0] * tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("floorEnemies").get(i)[1] * tilemap.getTILE_SIZE());
                            addToRoot(gameLayer, floorEnemy.getEnemyImage());
                            new Thread(floorEnemy).start();
                        }
                        break;
                    case "skyEnemies":
                        for (int i = 1; i <= mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("skyEnemies").size(); i++) {
                            SkyEnemy skyEnemy = new SkyEnemy(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/floorEnemy.png"))), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("skyEnemies").get(i)[2], player,
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("skyEnemies").get(i)[0] * tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("skyEnemies").get(i)[1] * tilemap.getTILE_SIZE());
                            addToRoot(gameLayer, skyEnemy.getEnemyImage());
                            new Thread(skyEnemy).start();
                        }
                        break;
                    case "jumpingEnemies":
                        for (int i = 1; i <= mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("jumpingEnemies").size(); i++) {
                            JumpingEnemy jumpingEnemy = new JumpingEnemy(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Creatures/jumpingEnemy.png"))), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("jumpingEnemies").get(i)[2], player,
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getMapData(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("jumpingEnemies").get(i)[0] * tilemap.getTILE_SIZE(),
                                    mapDataReader.getMapHm().get(getSelectedLevel()).getEnemies().get("jumpingEnemies").get(i)[1] * tilemap.getTILE_SIZE());
                            addToRoot(gameLayer, jumpingEnemy.getEnemyImage());
                            new Thread(jumpingEnemy).start();
                        }
                }
            }

            heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
            heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
            heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));

            addToRoot(gameLayer, heart1);
            addToRoot(gameLayer, heart2);
            addToRoot(gameLayer, heart3);

            heart1.setX(-10);
            heart1.setY(-15);

            heart2.setX(55);
            heart2.setY(-15);

            heart3.setX(120);
            heart3.setY(-15);

            player.getPlayerImage().toFront();

            gameplayScene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
            gameplayScene.setOnKeyPressed(event -> {
                pressedKeys.add(event.getCode());
                if (event.getCode().equals(KeyCode.F11) && primaryStage.fullScreenProperty().get()) {
                    primaryStage.setFullScreen(false);
                } else if (event.getCode().equals(KeyCode.F11) && !primaryStage.fullScreenProperty().get()) {
                    primaryStage.setFullScreen(true);
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                    stopTimer();
                    loadExitMenu();
                }
            });
            gameplayScene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

            AnimationTimer gameLoop = new AnimationTimer() {
                public void handle(long now) {
                    updateGame(tilemap.getTileMapPattern(), tilemap.SCREEN_WIDTH, tilemap, bg1, bg2);
                }
            };
            gameLoop.start();

            System.out.println(mapDataReader.getMapHm().get(getSelectedLevel()).getMapData().length);
            System.out.println(tilemap.getTileMapLengthInPixel() / tilemap.getTILE_SIZE());

            primaryStage.setTitle("Repus Oiram");
            primaryStage.setScene(gameplayScene);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Logo.png"))));
            primaryStage.fullScreenExitKeyProperty().setValue(KeyCodeCombination.NO_MATCH);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error when loading the game: " + e.getMessage());
        }
    }

    @FXML
    private void loadExitMenu() {
        try {
            // Wenn’s scho im Layer is, weg damit (sicherheitshalber)
            if (overlayContainer != null) {
                overlayLayer.getChildren().remove(overlayContainer);
            }
            // Frisch aus FXML laden
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/game_menu-view.fxml"));
            Parent menuOverlay = loader.load();
            GameMenuController menuCtrl = loader.getController();
            menuCtrl.setMenuOverlay(menuOverlay);
            menuCtrl.setGameplayApplication(this);

            overlayContainer = new StackPane(menuOverlay);
            StackPane.setAlignment(menuOverlay, Pos.CENTER);
            overlayContainer.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
                Bounds bounds = menuOverlay.getBoundsInParent();
                if (!bounds.contains(e.getX(), e.getY())) {
                    overlayLayer.getChildren().remove(overlayContainer);
                    overlayContainer = null;
                }
            });

            overlayLayer.getChildren().add(overlayContainer);
        } catch (IOException e) {
            System.out.println("Error while loading the exit menu: " + e.getMessage());
        }
    }

    public void onPlayerLoseLevel() {
        stopTimer();
        levelWon = false;
        gameplayScene.setOnKeyPressed(null);
        gameplayScene.setOnKeyReleased(null);
        loadEndScreen();
    }

    public void onPlayerWinLevel() {
        stopTimer();
        Gson gson = new Gson();
        try (FileWriter wr = new FileWriter("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/LocalUser.json")) {
            LevelMenuController.localUser.updateHighscores(getSelectedLevel(), getTime());
            gson.toJson(LevelMenuController.localUser, wr);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        levelWon = true;
        loadEndScreen();
        gameplayScene.setOnKeyPressed(null);
        gameplayScene.setOnKeyReleased(null);
    }


    /**
     * This method loads the Endscreen, whenever you win/lose.
     * It is called in the gameloop in the start(); method.
     */
    private void loadEndScreen() {
        Platform.runLater(() -> {
            if (!endScreenLoaded) {
                endScreenLoaded = true;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/endscreen-view.fxml"));
                    AnchorPane endScreenPane = loader.load();

                    Stage endScreenStage = new Stage();
                    endScreenStage.setTitle("Endscreen");
                    endScreenStage.setScene(new Scene(endScreenPane));
                    endScreenStage.setResizable(false);

                    endScreenStage.initOwner((Stage) gameLayer.getScene().getWindow());
                    endScreenStage.initModality(Modality.APPLICATION_MODAL);
                    endScreenStage.initStyle(StageStyle.UNDECORATED);
                    endScreenStage.show();
                } catch (IOException e) {
                    System.out.println("Error while loading the end screne: " + e.getMessage());
                }
            }
        });
    }

    /**
     * This function is continuously executed by the computer and enables gameplay.
     * player.checkPlayerLegalHeight(); -> Checks if the player has not fallen into the void.
     * player.playerMovementX(pressedKeys, map); -> Responsible for the player's left and right movement.
     * player.playerMovementY(map, pressedKeys, GRAVITY); -> Handles the player's vertical movement (jumping and falling).
     * moveRoot(player.getPlayerImage(), screenWidth, tilemap.getTileMapLengthInPixel(), tilemap.getTILE_SIZE()); -> Moves the root to display the new section of the level.
     * repeatBackground(bg1, bg2, offsetX); -> Repeats the background image.
     * checkActiveItems(); -> Checks for active player items and verifies when they expire.
     *
     * @param map
     * @param screenWidth
     * @param tilemap
     * @param bg1
     * @param bg2
     */
    private void updateGame(int[][] map, int screenWidth, Tilemap tilemap, ImageView bg1, ImageView bg2) {
        player.checkPlayerLegalHeight();
        player.playerMovementX(pressedKeys, map);
        player.playerMovementY(map, pressedKeys, GRAVITY);
        moveRoot(player.getPlayerImage(), screenWidth, tilemap.getTileMapLengthInPixel(), tilemap.getTILE_SIZE());
        repeatBackground(bg1, bg2, offsetX);
        checkActiveItems();
    }

    /**
     * Responsible for shifting the game or level when the player moves too far off-screen,
     * ensuring the relevant part of the level where the player is located stays in focus.
     *
     * @param player
     * @param screenWidth
     * @param totalTileLength
     * @param tileSize
     */

    public void moveRoot(ImageView player, int screenWidth, double totalTileLength, double tileSize) {
        double playerScreenX = player.getX() - offsetX;

        if (playerScreenX > screenWidth * 0.75) {
            offsetX += SCROLL_SPEED;
        } else if (playerScreenX < screenWidth * 0.25) {
            offsetX -= SCROLL_SPEED;
        }
        offsetX = Math.max(0, Math.min(offsetX, totalTileLength * tileSize - screenWidth));

        gameLayer.setTranslateX(-offsetX);
        timerDisplay.setTranslateX(offsetX);
        heart3.setTranslateX(offsetX);
        heart2.setTranslateX(offsetX);
        heart1.setTranslateX(offsetX);
        checkActiveItems();
    }

    /**
     * Adds an object to the root.
     *
     * @param root
     * @param node
     */

    public void addToRoot(Pane root, Node node) {
        root.getChildren().add(node);
    }

    public ImageView createBackGround(String filepath) {
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(filepath))));
    }

    /**
     * Updates the position of two background images to create an infinite scrolling effect.
     *
     * As one background moves out of view, it is repositioned to the opposite side
     * to repeat the background.
     *
     * @param bg1 The first background imageview
     * @param bg2 The second background imageview
     * @param offsetX The current horizontal scroll offset
     */

    public void repeatBackground(ImageView bg1, ImageView bg2, double offsetX) {
        double bgWidth = bg1.getImage().getWidth();

        if (bg1.getX() + bgWidth <= offsetX) {
            bg1.setX(bg2.getX() + bgWidth);
        }

        if (bg2.getX() + bgWidth <= offsetX) {
            bg2.setX(bg1.getX() + bgWidth);
        }

        if (bg1.getX() >= offsetX + bgWidth) {
            bg1.setX(bg2.getX() - bgWidth);
        }

        if (bg2.getX() >= offsetX + bgWidth) {
            bg2.setX(bg1.getX() - bgWidth);
        }
    }

    /**
     * This function sets the player's heart images.
     * The number of full hearts reflects the player's remaining lives.
     * This function is only executed when the player's HP value changes.
     */

    public void setHeartImagesBasedOnHp(){
        switch (player.getHp()) {
            case 3:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                break;
            case 2:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                break;
            case 1:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
                break;
            case 0:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                break;
        }
    }

    public static int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        GameplayApplication.selectedLevel = selectedLevel;
    }

    /**
     * Displays the timer on the screen by positioning and adding it to the game layer.
     *
     * Sets the timer's X and Y coordinates, assigns it an ID for styling purposes,
     * and adds it to the specified game layer.
     */
    public void displayTimer() {
        getTimerDisplay().setY(60);

        getTimerDisplay().setX(1340);

        getTimerDisplay().setId("timerDisplay");

        addToRoot(gameLayer, getTimerDisplay());
    }

    /**
     * Starts the in-game timer that updates every second.
     *
     * Increments the total seconds since the start and updates the displayed timer text
     * in "00:00" format, adding leading zeros for single-digit minutes and seconds.
     */

    public void startTimer() {
        setTimerTimeline(new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            setSecondsSinceStart(getSecondsSinceStart() + 1);
            int secs;
            int mins;

            secs = getSecondsSinceStart() % 60;
            mins = getSecondsSinceStart() / 60;

            setTime((mins < 10 ? "0" : "") + mins + ":" + (secs < 10 ? "0" : "") + secs);
            getTimerDisplay().setText(getTime());
        })));


        getTimerTimeline().setCycleCount(Timeline.INDEFINITE);
        getTimerTimeline().play();
    }

    /**
     * Checks if an item is active and, if so,
     * verifies how long it has been running. If it runs for too long,
     * the effect disappears.
     */
    public void checkActiveItems(){
        if(player.superboots.isActive()){
            player.superboots.bootsactivecheck();
        }
        if(player.superUmhang.isActive()){
            player.superUmhang.isactivateSuperCape();
        }
    }

    /**
     * stops the in-game timer
     */
    public void stopTimer() {
        getTimerTimeline().stop();
    }

    /**
     * resumes the in-game timer
     */
    public void resumeTimer() {
        getTimerTimeline().play();
    }

    public Timeline getTimerTimeline() {
        return timerTimeline;
    }

    public void setTimerTimeline(Timeline timerTimeline) {
        this.timerTimeline = timerTimeline;
    }

    public int getSecondsSinceStart() {
        return secondsSinceStart;
    }

    public void setSecondsSinceStart(int secondsSinceStart) {
        this.secondsSinceStart = secondsSinceStart;
    }

    public Text getTimerDisplay() {
        return timerDisplay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ImageView getBg1() {
        return bg1;
    }

    public void setBg1(ImageView bg1) {
        this.bg1 = bg1;
    }

    public ImageView getBg2() {
        return bg2;
    }

    public void setBg2(ImageView bg2) {
        this.bg2 = bg2;
    }
}