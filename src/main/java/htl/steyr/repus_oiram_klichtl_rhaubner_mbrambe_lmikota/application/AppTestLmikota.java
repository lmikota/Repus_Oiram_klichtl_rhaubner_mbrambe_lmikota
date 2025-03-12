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

import java.io.IOException;

public class AppTestLmikota extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        /// @ToDo
        /// Exception handling
        try {
            MapDataReader mapDataReader;
            mapDataReader = new MapDataReader();
            Tilemap tilemap = new Tilemap(mapDataReader.getMapData(), 40);


            Pane root = new Pane();
            root.getChildren().add(tilemap.getTyleMapPane());

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Repus Oiram");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
