package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SkyEnemy extends Enemy implements Runnable {
    public SkyEnemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player);
    }

    @Override
    public void run() {
        while(!isDead()) {
            try{
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(20);
                    setEnemyX(getEnemyX()+2);
                    getEnemyImage().setX(getEnemyX());
                    checkPlayerHitBox();

                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }
                }
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(20);
                    setEnemyX(getEnemyX()-2);
                    getEnemyImage().setX(getEnemyX());
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
