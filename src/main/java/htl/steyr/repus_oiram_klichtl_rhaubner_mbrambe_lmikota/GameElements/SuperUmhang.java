package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.Duration;
import java.time.LocalTime;

public class SuperUmhang extends Item{
    private ImageView supercape;
    private LocalTime start = LocalTime.now();
    private LocalTime end;
    private Duration duration;

    public SuperUmhang(Player player,Image imagecape, double capesize) {
        super("Super-Umhang", "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/GameElements/Items/SuperCape.png");

        setImagecape(new ImageView(imagecape));
        setImagesize(capesize);
    }

    public void activateCapeEffect(Player pl){
        pl.setCapeEffect(true);
        start = LocalTime.now();
    }

    public void isactivateSuperCape(Player player) {
        end = LocalTime.now();
        duration = Duration.between(start, end);
        double deltaTime = duration.toSeconds();
        if (deltaTime >= 30.0) {
            player.setCapeEffect(false);
        }
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
