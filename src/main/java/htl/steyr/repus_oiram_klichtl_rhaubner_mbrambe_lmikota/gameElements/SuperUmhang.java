package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SuperUmhang extends Item{
    private ImageView supercape;

    public SuperUmhang(Image imagecape, double capesize) {
        super("Super-Umhang", "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/SuperCape.png");

        setImagecape(new ImageView(imagecape));
        setImagesize(capesize);
    }

    public void activateSuperCape() {

    }

    public ImageView getImagecape() {
        return supercape;
    }

    public void setImagecape(ImageView supertrank) {
        this.supercape = supertrank;
    }

    public void setImagesize(double tranksize){
        getImagecape().setFitWidth(tranksize);
        getImagecape().setFitHeight(tranksize);
        getImagecape().minHeight(tranksize);
        getImagecape().minWidth(tranksize);
        getImagecape().maxHeight(tranksize);
        getImagecape().maxWidth(tranksize);
    }
}
