package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.Player;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.Tilemap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AppTestLmikota extends Application {
    private static final int SCROLL_SPEED = 10;
    private final double GRAVITY = 0.5;
    private int playerSize = 90;
    private double offsetX = 0;

    private Player player;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /// @ToDo
        /// Exception handling
        /// Fullscreen fixen
        try {
            MapDataReader mapDataReader;
            mapDataReader = new MapDataReader();
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            //screenHeight = 2160;
            //screenWidth =  3840;
            System.out.println(screenWidth);
            System.out.println(screenHeight);
            int dynamicTileSize = (screenHeight * screenWidth) / 23500;
            System.out.println(dynamicTileSize);
            Tilemap tilemap = new Tilemap(mapDataReader.getMapData());


            Pane root = new Pane();
            root.getChildren().add(tilemap.getTyleMapPane());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            player = new Player(new Image(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/Character_Repus.png")), tilemap.getTILE_SIZE(), tilemap.getTILE_SIZE());
            addToRoot(root, player.getPlayerImage());

            scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
            scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

            AnimationTimer gameLoop = new AnimationTimer() {
                public void handle(long now) {
                    updateGame(mapDataReader.getMapData(), root, 1200);
                }
            };
            gameLoop.start();

            stage.setTitle("Repus Oiram");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateGame(int map[][], Pane root, int screenWidth) {
        player.playerMovementX(pressedKeys, map);
        player.playerMovementY(map, pressedKeys, GRAVITY);
        moveRoot(root, player.getPlayerImage(), screenWidth);
    }

    public void moveRoot(Pane root, ImageView player, int screenWidth) {
        double playerScreenX = player.getX() - offsetX;

        if (playerScreenX > screenWidth * 0.75) {
            offsetX += SCROLL_SPEED;
        } else if (playerScreenX < screenWidth * 0.25) {
            offsetX -= SCROLL_SPEED;
        }
        offsetX = Math.max(0, Math.min(offsetX, 1400 * 40 - screenWidth));
        root.setTranslateX(-offsetX);
    }

    public void addToRoot(Pane root, Node node) {
        root.getChildren().add(node);
    }
}
