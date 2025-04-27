package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class SkyEnemy extends Enemy implements Runnable {
    /**
     * Der SkyEnemy erbt von Enemy klasse
     * Dieser Gegener bewekt sich nach links und
     * rechts in der luft ohne das schwerkarft auf ihn wirkt
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
     * Diese run funktion simuliert die bewegung des Gegners.
     * Der Gegner läuft solange er noch am Leben ist bzw solagne die
     * isDead variable false ist.
     * Die zwei for schleife simulieren einmal die bewegung nach linkse
     * und anderseits die bewegung nach rechts.
     * bei jeden durchlauf der for schleife wird die checkPlayerHitBox()
     * funktion ausgefürht um zu checken ob der spieler den gegner berührt.
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
