package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SuperTrank extends Item {
    private Player player;

    public SuperTrank(Player player) {
        super("Super-Trank");
        setPlayer(player);
    }

    public void activateTrankEffect() {
        getPlayer().setHp(getPlayer().getHp() + 1);
        System.out.println("Super-Trank activated + 1 live");
        System.out.println(getPlayer().getHp());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}