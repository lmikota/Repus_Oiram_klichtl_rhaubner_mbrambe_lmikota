package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class SkyEnemy extends Enemy implements Runnable {
    public SkyEnemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player);
    }

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
