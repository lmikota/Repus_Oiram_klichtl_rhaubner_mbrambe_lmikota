package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String backgroundMusicFilePath = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/WAV/backgroundmusic.wav";
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.playMusic(backgroundMusicFilePath);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/FXML/start_menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Logo.png"))));
        stage.setTitle("Repus Oiram");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}