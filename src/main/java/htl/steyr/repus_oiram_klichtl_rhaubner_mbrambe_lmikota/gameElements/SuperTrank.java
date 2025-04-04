package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

public class SuperTrank extends Item {
    private ImageView supertrank;

    public SuperTrank(Player player, Image image, double tranksize) {
        super("Super-Trank",
                "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/superTrank.png");

        setImagetrank(new ImageView(image));
        setImagesize(tranksize);
    }

    public void activateSuperTrank(Player player) {
        player.setHp(player.getHp() + 1);
    }

    public void setImagetrank(ImageView supertrank) {
        this.supertrank = supertrank;
    }

    public ImageView getImagetrank() {
        return supertrank;
    }

    public void setImagesize(double tranksize){
        getImagetrank().setFitWidth(tranksize);
        getImagetrank().setFitHeight(tranksize);
        getImagetrank().minHeight(tranksize);
        getImagetrank().minWidth(tranksize);
        getImagetrank().maxHeight(tranksize);
        getImagetrank().maxWidth(tranksize);
    }
}