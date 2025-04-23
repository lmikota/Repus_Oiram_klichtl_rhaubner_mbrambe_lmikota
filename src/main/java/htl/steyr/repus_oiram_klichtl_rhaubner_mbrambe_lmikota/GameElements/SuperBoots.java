package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.Duration;
import java.time.LocalTime;

public class SuperBoots extends Item{
    private LocalTime start = LocalTime.now();
    private LocalTime end;
    private Duration duration;
    private Player player;

    public SuperBoots(Player player) {
        super("Super-Boots");
        setPlayer(player);
    }

    public void activateBootsEffect() {
        getPlayer().setJUMP_SPEED(-20);
        start = LocalTime.now();
    }

    public void bootsactivecheck() {
        end = LocalTime.now();
        duration = Duration.between(start, end);
        double deltaTime = duration.toSeconds();
        if (deltaTime >= 20.0) {
            getPlayer().setJUMP_SPEED(-15);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
