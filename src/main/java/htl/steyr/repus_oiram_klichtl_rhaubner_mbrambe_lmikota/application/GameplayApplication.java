package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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

public class GameplayApplication extends Application {
    private static final int SCROLL_SPEED = 10;
    private final double GRAVITY = 0.5;
    private double offsetX = 0;
    private int selectedLevel;
    public static Scene gameplayScene;

    private Player player;
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

            gameplayScene = new Scene(root);
            Stage stage = new Stage();

            player = new Player(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Character_Repus.png")), tilemap.getTILE_SIZE(), tilemap.getTILE_SIZE());
            addToRoot(root, player.getPlayerImage());

            SuperTrank superTrank = new SuperTrank(player ,new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/superTrank.png")), tilemap.getTILE_SIZE()/1.5);
            addToRoot(root,superTrank.getImagetrank());

            SuperUmhang superUmhang = new SuperUmhang(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/SuperCape.png")), tilemap.getTILE_SIZE()/1.5);
            addToRoot(root, superUmhang.getImagecape());

//            FloorEnemy gegner = new FloorEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/bodenmonster.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player, mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
//            addToRoot(root, gegner.getEnemyImage());
//            Thread gegnerThread = new Thread(gegner);
//            gegnerThread.start();
//
//            SkyEnemy skyEnemy = new SkyEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/sprungmonster.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 40, player);
//            addToRoot(root, skyEnemy.getEnemyImage());
//            Thread skyEnemyThread = new Thread(skyEnemy);
//            skyEnemyThread.start();
//
//            JumpingEnemy jumpingEnemy = new JumpingEnemy(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Vogel.png")), (int) tilemap.getTILE_SIZE(), (int) tilemap.getTILE_SIZE(), 100, player, mapDataReader.getMapHm().get(getSelectedLevel()).getMapData());
//            addToRoot(root, jumpingEnemy.getEnemyImage());
//            Thread jumpingEnemyThread = new Thread(jumpingEnemy);
//            jumpingEnemyThread.start();

            player.getPlayerImage().toFront();

            gameplayScene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
            gameplayScene.setOnKeyPressed(event -> {
                pressedKeys.add(event.getCode());
                if (event.getCode().equals(KeyCode.F11) && stage.fullScreenProperty().get()) {
                    stage.setFullScreen(false);
                } else if (event.getCode().equals(KeyCode.F11) && !stage.fullScreenProperty().get()) {
                    stage.setFullScreen(true);
                } else if (event.getCode().equals(KeyCode.ESCAPE)) {
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

            stage.setTitle("Repus Oiram");
            stage.setScene(gameplayScene);
            stage.setFullScreen(true);
            stage.setFullScreenExitHint("");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Logo.png")));
            stage.fullScreenExitKeyProperty().setValue(KeyCodeCombination.NO_MATCH);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExitMenu(Pane root) {
        System.out.println("load Exitmenu"); // debugging
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML-Files/exit_menu-view.fxml"));

        Platform.runLater(() -> {
            try {
                Pane exitMenu = loader.load();

                Stage exitStage = new Stage();
                exitStage.setTitle("Exit Menu");
                exitStage.setScene(new Scene(exitMenu));
                exitStage.setResizable(false);

                exitStage.initOwner(root.getScene().getWindow());
                exitStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                exitStage.show();

                System.out.println("Exit-Menü erfolgreich geöffnet!"); //debugging
            } catch (IOException e) {
                System.out.println("Fehler beim Laden des Exit-Menüs: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }


    private void updateGame(int[][] map, Pane root, int screenWidth, Tilemap tilemap, ImageView bg1, ImageView bg2) {
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
}
