package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.Player;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements.Tilemap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class AppTestLmikota extends Application {

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
            int dynamicTileSize = (screenHeight * screenWidth) / 40000;
            System.out.println(dynamicTileSize);
            Tilemap tilemap = new Tilemap(mapDataReader.getMapData(), dynamicTileSize);


            Pane root = new Pane();
            root.getChildren().add(tilemap.getTyleMapPane());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Repus Oiram");
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
