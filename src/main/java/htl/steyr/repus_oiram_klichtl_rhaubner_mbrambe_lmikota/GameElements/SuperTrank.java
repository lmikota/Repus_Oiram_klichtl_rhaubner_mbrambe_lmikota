package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SuperTrank extends Item {
    private Player player;

    /**
     * Constructor for the Item SuperTrank
     * @param player
     */
    public SuperTrank(Player player) {
        super("Super-Trank");
        setPlayer(player);
    }

    /**
     * When the methode is called the player regains one
     * hp/live
     */
    public void activateTrankEffect() {
        if(getPlayer().getHp() < 3){
            getPlayer().setHp(getPlayer().getHp() + 1);
            System.out.println(getPlayer().getHp() + ": das sind die leben");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}