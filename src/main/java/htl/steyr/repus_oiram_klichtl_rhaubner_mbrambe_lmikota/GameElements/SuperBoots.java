package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.Duration;
import java.time.LocalTime;

public class SuperBoots extends Item{
    private ImageView superboots;
    private LocalTime start = LocalTime.now();
    private LocalTime end;
    private Duration duration;

    public SuperBoots(Player player, Image image, double bootsize) {
        super("Super-Boots", "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/superTrank.png");
        setSuperboots(new ImageView(image));
        setImagesize(bootsize);
    }

    public void activateBootsEffect(Player player) {
        player.setJUMP_SPEED(-30);
        start = LocalTime.now();
    }

    public void bootsactivecheck(Player player) {
        end = LocalTime.now();
        duration = Duration.between(start, end);
        double deltaTime = duration.toSeconds();
        if (deltaTime >= 20.0) {
            player.setJUMP_SPEED(-15);
        }
    }

    public void setImagesize(double tranksize){
        getSuperboots().setFitWidth(tranksize);
        getSuperboots().setFitHeight(tranksize);
        getSuperboots().minHeight(tranksize);
        getSuperboots().minWidth(tranksize);
        getSuperboots().maxHeight(tranksize);
        getSuperboots().maxWidth(tranksize);
    }

    public ImageView getSuperboots() {
        return superboots;
    }

    public void setSuperboots(ImageView superboots) {
        this.superboots = superboots;
    }
}
