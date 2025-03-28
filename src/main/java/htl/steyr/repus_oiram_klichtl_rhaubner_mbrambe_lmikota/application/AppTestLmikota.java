package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AppTestLmikota extends Application {
    private static final int SCROLL_SPEED = 10;
    private final double GRAVITY = 0.5;
    private double offsetX = 0;
    private int selectedLevel;

    private Player player;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            MapDataReader mapDataReader;
            mapDataReader = new MapDataReader();
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            System.out.println("widht: " + screenWidth);
            System.out.println("height: " + screenHeight);
            Tilemap tilemap = new Tilemap(mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
            ImageView bg1 = createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Level_Backgrounds/Level_1_Background/Level_1-Background_1.png");
            ImageView bg2 = createBackGround("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Level_Backgrounds/Level_1_Background/Level_1-Background_2.png");
            Pane root = new Pane();
            addToRoot(root, bg1);
            addToRoot(root, bg2);
            bg2.setX(bg1.getImage().getWidth());
            addToRoot(root, tilemap.getTyleMapPane());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            player = new Player(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Character_Repus.png")), tilemap.getTILE_SIZE(), tilemap.getTILE_SIZE());
            addToRoot(root, player.getPlayerImage());

            FloorEnemy gegner = new FloorEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/ghost.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player, mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
            addToRoot(root, gegner.getEnemyImage());
            Thread gegnerThread = new Thread(gegner);
            gegnerThread.start();

            SkyEnemy skyEnemy = new SkyEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/ghost.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player);
            addToRoot(root, skyEnemy.getEnemyImage());
            Thread skyEnemyThread = new Thread(skyEnemy);
            skyEnemyThread.start();


            player.getPlayerImage().toFront();

            scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
            scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

            AnimationTimer gameLoop = new AnimationTimer() {
                public void handle(long now) {
                    updateGame(tilemap.getTileMapPattern(), root, tilemap.SCREEN_WIDTH, tilemap, bg1, bg2);
                }
            };
            gameLoop.start();

            stage.setTitle("Repus Oiram");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
//            stage.fullScreenExitKeyProperty().setValue(KeyCodeCombination.NO_MATCH);
//            scene.setOnKeyPressed( keyEvent ->  {
//                if(keyEvent.getCode().equals(KeyCode.F11) && stage.fullScreenProperty().get()) {
//                    stage.setFullScreen(false);
//                } else if (keyEvent.getCode().equals(KeyCode.F11) && !stage.fullScreenProperty().get()) {
//                    stage.setFullScreen(true);
//                }
//            }); geht ned weil des mitn Robin kollanbiert
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGame(int map[][], Pane root, int screenWidth, Tilemap tilemap, ImageView bg1, ImageView bg2) {
        player.playerMovementX(pressedKeys, map);
        player.playerMovementY(map, pressedKeys, GRAVITY);
        moveRoot(root, player.getPlayerImage(), screenWidth, tilemap.getTileMapLengthInPixel(), tilemap.getTILE_SIZE());
        repeatBackground(bg1, bg2, offsetX);
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
    }

    public int getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(int selectedLevel) {
        this.selectedLevel = selectedLevel;
    }
}
