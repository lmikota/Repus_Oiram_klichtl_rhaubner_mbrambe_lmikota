package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import java.time.Duration;
import java.time.LocalTime;

public class SuperUmhang extends Item{
    private LocalTime start;
    private Player player;

    public SuperUmhang(Player player) {
        super("Super-Umhang");
        setPlayer(player);
    }

    public void activateCapeEffect(){
        getPlayer().setCapeEffect(true);
        setActive(true);
        start = LocalTime.now();
    }

    public void isactivateSuperCape() {
        LocalTime currentTime = LocalTime.now();
        long duration = Duration.between(start, currentTime).toSeconds();

        if (duration >= 30.0) {
            getPlayer().setCapeEffect(false);
            setActive(false);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
