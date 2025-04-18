package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application;

import com.google.gson.Gson;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller.LevelMenuController;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.LocalUserReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameplayApplication extends Application implements Initializable {
    private static final int SCROLL_SPEED = 4;
    private final double GRAVITY = 0.5;
    private double offsetX = 0;
    private int selectedLevel;
    public static Scene gameplayScene;
    private String time;
    private ImageView bg1;
    private ImageView bg2;

    private Timeline timerTimeline;

    private int SecondsSinceStart = 0;

    private static MapDataReader mapDataReader;

    @FXML
    public static Text timerDisplay = new Text();

    ImageView heart1 = new ImageView();
    ImageView heart2 = new ImageView();
    ImageView heart3 = new ImageView();

    private Player player;
    private SuperUmhang cape;
    private SuperBoots boots;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /**
         * @ToDo
         * Background je nach Level matchen und ALLE 3 Hintergründe implementieren (erst wenn der Rest fertig ist, weil des ned elementar was am game ändert).
         * Zuerst Projektmanagement usw. AP, Levels Bauen, Robin oder Marcel unterstützen bei Items/Gegner
         */
        try {
            mapDataReader = new MapDataReader();
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            System.out.println("widht: " + screenWidth);
            System.out.println("height: " + screenHeight);
            Tilemap tilemap = new Tilemap(mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
            setBg1(createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/LevelBackgrounds/Level_"+getSelectedLevel()+"_Background/Level_"+getSelectedLevel()+"-Background_1.png"));
            setBg2(createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/LevelBackgrounds/Level_"+getSelectedLevel()+"_Background/Level_"+getSelectedLevel()+"-Background_2.png"));
            Pane root = new Pane();
            startTimer();
            addToRoot(root, bg1);
            addToRoot(root, bg2);
            displayTimer(tilemap, root);
            getBg2().setX(bg1.getImage().getWidth());
            addToRoot(root, tilemap.getTyleMapPane());

            gameplayScene = new Scene(root);
            gameplayScene.getStylesheets().add(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/CSS/gameElementsStyle.css").toExternalForm());

            player = new Player(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/Character_Repus.png")), tilemap.getTILE_SIZE(), tilemap.getTILE_SIZE(), this);
            addToRoot(root, player.getPlayerImage());

            SuperTrank superTrank = new SuperTrank(player, new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Items/superTrank.png")), tilemap.getTILE_SIZE() / 1.5);
            addToRoot(root, superTrank.getImagetrank());

            cape = new SuperUmhang(player, new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Items/SuperCape.png")), tilemap.getTILE_SIZE() / 1.5);
            addToRoot(root, cape.getImagecape());

            boots = new SuperBoots(player, new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Items/SuperBoots.png")), tilemap.getTILE_SIZE() / 1.5);
            addToRoot(root, boots.getSuperboots());

            FloorEnemy gegner = new FloorEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/ghost.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player, mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
            addToRoot(root, gegner.getEnemyImage());
            Thread gegnerThread = new Thread(gegner);
            gegnerThread.start();

            SkyEnemy skyEnemy = new SkyEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/bodenmonster.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player);
            addToRoot(root, skyEnemy.getEnemyImage());
            Thread skyEnemyThread = new Thread(skyEnemy);
            skyEnemyThread.start();

            heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
            heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));
            heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/fullHeart.png"))));

            addToRoot(root, heart1);
            addToRoot(root, heart2);
            addToRoot(root, heart3);

            heart1.setX(250);
            heart1.setY(15);

            heart2.setX(315);
            heart2.setY(15);

            heart3.setX(380);
            heart3.setY(15);

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
                    loadExitMenu(root);
                }
            });
            gameplayScene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

            AnimationTimer gameLoop = new AnimationTimer() {
                public void handle(long now) {
                    updateGame(tilemap.getTileMapPattern(), root, tilemap.SCREEN_WIDTH, tilemap, bg1, bg2);
                }
            };
            gameLoop.start();

            primaryStage.setTitle("Repus Oiram");
            primaryStage.setScene(gameplayScene);
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Logo.png")));
            primaryStage.fullScreenExitKeyProperty().setValue(KeyCodeCombination.NO_MATCH);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExitMenu(Pane root) {
        System.out.println("load Exitmenu"); // debugging
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/exit_menu-view.fxml"));

        try {
            Pane exitMenu = loader.load();

            Stage exitStage = new Stage();
            exitStage.setTitle("Exit Menu");
            exitStage.setScene(new Scene(exitMenu));
            exitStage.setResizable(false);

            exitStage.initOwner(root.getScene().getWindow());
            exitStage.initModality(Modality.APPLICATION_MODAL);
            exitStage.show();
        } catch (IOException e) {
            System.out.println("Fehler beim Laden des Exit-Menüs: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) gameplayScene.getWindow();
        stage.close();
    }

    public void onPlayerLoseLevel() {
        closeWindow();
    }

    /**
     * @ToDo
     * winLevel(); erst aufrufen, wenn der spieler den Turm erreicht
     */
    public void onPlayerWinLevel() {
        Gson gson = new Gson();
        try (FileWriter wr = new FileWriter("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/LocalUser.json")) {
            LevelMenuController.localUser.updateHighscores(getSelectedLevel(), getTime());
            gson.toJson(LevelMenuController.localUser, wr);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        closeWindow();
    }

    private void updateGame(int[][] map, Pane root, int screenWidth, Tilemap tilemap, ImageView bg1, ImageView bg2) {
        player.checkPlayerLegalHeight();
        switch (player.getHp()) {
            case 3:
                break;
            case 2:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                break;
            case 1:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                break;
            case 0:
                heart3.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                heart1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/emptyHeart.png"))));
                break;
        }
        player.playerMovementX(pressedKeys, map);
        player.playerMovementY(map, pressedKeys, GRAVITY);
        moveRoot(root, player.getPlayerImage(), screenWidth, tilemap.getTileMapLengthInPixel(), tilemap.getTILE_SIZE());
        repeatBackground(bg1, bg2, offsetX);
        cape.isactivateSuperCape(player);
    }

    public void moveRoot(Pane root, ImageView player, int screenWidth, double totalTileLength, double tileSize) {
        double playerScreenX = player.getX() - offsetX;

        if (playerScreenX > screenWidth * 0.75) {
            offsetX += SCROLL_SPEED;
        } else if (playerScreenX < screenWidth * 0.25) {
            offsetX -= SCROLL_SPEED;
        }
        offsetX = Math.max(0, Math.min(offsetX, totalTileLength * tileSize - screenWidth));

        root.setTranslateX(-offsetX);
        timerDisplay.setTranslateX(offsetX);
    }

    public void addToRoot(Pane root, Node node) {
        root.getChildren().add(node);
    }

    public ImageView createBackGround(String filepath) {
        return new ImageView(new Image(getClass().getResourceAsStream(filepath)));
    }

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

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void displayTimer(Tilemap tilemap, Pane root) {

        getTimerDisplay().setY((double) tilemap.SCREEN_HEIGHT / 10);

        getTimerDisplay().setX((double) tilemap.SCREEN_WIDTH / 10);

        getTimerDisplay().setId("timerDisplay");

        addToRoot(root, getTimerDisplay());
        System.out.println("in root drinnen"); // debugging
    }


    public void startTimer() {

        setTimerTimeline(new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            setSecondsSinceStart(getSecondsSinceStart() + 1);
            int secs;
            int mins;

            secs = getSecondsSinceStart() % 60;
            mins = getSecondsSinceStart() / 60;

            setTime((mins < 10 ? "0" : "") + mins + ":" + (secs < 10 ? "0" : "") + secs);
            getTimerDisplay().setText(getTime());
            // System.out.println(time); debugging
        })));


        getTimerTimeline().setCycleCount(Timeline.INDEFINITE);
        getTimerTimeline().play();
    }

    public void stopTimer() {
        getTimerTimeline().stop();
    }

    public Timeline getTimerTimeline() {
        return timerTimeline;
    }

    public void setTimerTimeline(Timeline timerTimeline) {
        this.timerTimeline = timerTimeline;
    }

    public int getSecondsSinceStart() {
        return SecondsSinceStart;
    }

    public void setSecondsSinceStart(int secondsSinceStart) {
        SecondsSinceStart = secondsSinceStart;
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

