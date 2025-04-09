package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;

import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

public class JumpingEnemy extends Enemy implements Runnable {
    private int map[][];
    private double enemyVelY = 0;
    private boolean isBlockUnderIt = false;
    private double enemyGravity = 0.4;

    private LocalTime lastJump;

    private boolean isJumping = false;
    private int jumpSpeed = -24;

    public JumpingEnemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player, int map[][]) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player);
        this.map = map;
        lastJump = LocalTime.now();
    }

    @Override
    public void run() {
        while(!isDead()) {
            try{
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }

                    Thread.sleep(20);
                    setEnemyX(getEnemyX()+2);
                    getEnemyImage().setX(getEnemyX());
                    checkPlayerHitBox();

                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }

                    gravityOnEnemy();
                }
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }

                    Thread.sleep(20);
                    setEnemyX(getEnemyX()-2);
                    getEnemyImage().setX(getEnemyX());
                    checkPlayerHitBox();

                    if(isDead()){
                        getEnemyImage().setDisable(true);
                        getEnemyImage().setVisible(false);
                        return;
                    }

                    gravityOnEnemy();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void gravityOnEnemy(){
        LocalTime now = LocalTime.now();

        Duration duration = Duration.between(lastJump, now);
        double deltaTime = duration.toSeconds();

        if(deltaTime > 1 && isBlockUnderIt) {
            enemyVelY = jumpSpeed;
            lastJump = now;
        }

        enemyVelY += enemyGravity;
        double newYPosition = getEnemyImage().getY() + enemyVelY;

        int blockY = (int) ((newYPosition + getEnemyImage().getFitHeight()) / getTileSize());

        for (int i = 0; i < getEnemySize(); i++) {
            int blockX = (int) ((getEnemyImage().getX() + i) / getTileSize());

            if (blockX >= 0 && blockX < map[0].length) {
                if (blockY >= 0 && blockY < map.length && isBlockSolid(map[blockY][blockX])) {
                    isBlockUnderIt = true;
                    break;
                }else{
                    isBlockUnderIt = false;
                }
            }
        }

        if (isBlockUnderIt) {
            getEnemyImage().setY(blockY * getTileSize() - getEnemyImage().getFitHeight());
            enemyVelY = 0;
        }else {
            getEnemyImage().setY(newYPosition);
            setEnemyY(newYPosition);
        }
    }

    public boolean isBlockSolid(int block) {
        return block == 1 || block == 2 || block == 3 || block == 4 || block == 5 || block == 6 || block == 7 || block == 187 ;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int map[][]) {
        this.map = map;
    }

    public double getEnemyVelY() {
        return enemyVelY;
    }

    public void setEnemyVelY(double enemyVelY) {
        this.enemyVelY = enemyVelY;
    }
}
