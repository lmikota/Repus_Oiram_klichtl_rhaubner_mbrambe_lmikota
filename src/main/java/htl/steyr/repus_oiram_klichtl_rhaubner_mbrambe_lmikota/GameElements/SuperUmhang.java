package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.Duration;
import java.time.LocalTime;

public class SuperUmhang extends Item{
    private Player player;
    private LocalTime start = LocalTime.now();
    private LocalTime end;
    private Duration duration;

    public SuperUmhang(Player player) {
        super("Super-Umhang");
        setPlayer(player);
    }

    public void activateCapeEffect(){
        getPlayer().setCapeEffect(true);
        start = LocalTime.now();
    }

    public void isactivateSuperCape() {
        end = LocalTime.now();
        duration = Duration.between(start, end);
        double deltaTime = duration.toSeconds();
        if (deltaTime >= 30.0) {
            getPlayer().setCapeEffect(false);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
