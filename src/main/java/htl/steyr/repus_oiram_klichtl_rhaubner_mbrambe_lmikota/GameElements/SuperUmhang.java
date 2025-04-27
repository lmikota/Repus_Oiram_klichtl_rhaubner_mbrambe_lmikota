package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import java.time.Duration;
import java.time.LocalTime;

public class SuperUmhang extends Item{
    private LocalTime start;
    private Player player;

    /**
     * Constructor for the Item SuperUmhang
     * @param player
     */
    public SuperUmhang(Player player) {
        super("Super-Umhang");
        setPlayer(player);
    }

    /**
     * the capeeffect is set active
     */
    public void activateCapeEffect(){
        getPlayer().setCapeEffect(true);
        setActive(true);
        start = LocalTime.now();
    }

    /**
     * checks if 30 seconds have past if so the capeeffect is
     * set back to false
     */
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
