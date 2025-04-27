package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class SkyEnemy extends Enemy implements Runnable {
    /**
     * The SkyEnemy inherits from the Enemy class.
     * This enemy moves left and right in the air
     * without being affected by gravity.
     *
     * @param enemyImage
     * @param enemySize
     * @param tileSize
     * @param enemyMovementX
     * @param player
     * @param xCord
     * @param yCord
     */

    public SkyEnemy(Image enemyImage, int enemySize, int tileSize, double enemyMovementX, Player player, double xCord, double yCord) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player, xCord, yCord);
    }

    /**
     * This run function simulates the movement of the enemy.
     * The enemy moves as long as it is still alive or as long as
     * the isDead variable is false.
     * The two for loops simulate movement to the left
     * and movement to the right.
     * In each iteration of the for loop, the checkPlayerHitBox()
     * function is executed to check if the player touches the enemy.
     */

    @Override
    public void run() {
        while(!isDead()) {
            try{
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(15);
                    setEnemyX(getEnemyX()+2);

                    Platform.runLater(()->{
                        getEnemyImage().setX(getEnemyX());
                    });

                    checkPlayerHitBox();

                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }
                }
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(15);
                    setEnemyX(getEnemyX()-2);

                    Platform.runLater(()->{
                        getEnemyImage().setX(getEnemyX());
                    });

                    checkPlayerHitBox();

                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
