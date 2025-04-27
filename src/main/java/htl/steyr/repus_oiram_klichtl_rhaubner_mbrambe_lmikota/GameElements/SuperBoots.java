package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import java.time.Duration;
import java.time.LocalTime;

public class SuperBoots extends Item{
    private LocalTime start;
    private Player player;

    /**
     * Constructor for the Item SuperBoots
     * @param player
     */
    public SuperBoots(Player player) {
        super("Super-Boots");
        setPlayer(player);
    }

    /**
     * Jumpspeed is raised and is set active
     */
    public void activateBootsEffect() {
        getPlayer().setJUMP_SPEED(-20);
        setActive(true);
        start = LocalTime.now();
    }

    /**
     * checks if 20 seconds have past if so the jumping speed is
     * set back to normal again
     */
    public void bootsactivecheck() {
        LocalTime currentTime = LocalTime.now();
        long duration = Duration.between(start, currentTime).toSeconds();

        if (duration >= 20.0) {
            getPlayer().setJUMP_SPEED(-15);
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
