package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;

public class JumpingEnemy extends FloorEnemy{
    public JumpingEnemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player, int[][] map) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player, map);
    }


}
