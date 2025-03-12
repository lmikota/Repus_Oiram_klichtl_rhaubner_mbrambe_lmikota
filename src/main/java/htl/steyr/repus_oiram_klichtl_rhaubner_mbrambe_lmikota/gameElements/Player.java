package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Player {
    private ImageView playerImage;
    private int playerSize;

    public Player(Image playerImage, int playerSize) {
        setPlayerImage(playerImage);
        setPlayerSize(playerSize);
        changePlayerSize(this.playerSize);
    }

    public void changePlayerSize(int playerSize) {
        playerImage.setFitHeight(playerSize);
        playerImage.setFitWidth(playerSize);
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = new ImageView(playerImage);
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }
}
