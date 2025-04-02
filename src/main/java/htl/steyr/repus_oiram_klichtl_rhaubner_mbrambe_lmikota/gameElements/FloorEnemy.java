package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;

public class FloorEnemy extends Enemy implements Runnable {
    private int map[][];
    private int enemyVelY = 0;
    private boolean isBlockUnderIt;
    private double enemyGravity = 4;

    public FloorEnemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player, int map[][]) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player);
        this.map = map;
    }

    @Override
    public void run() {
        while(true) {
            try{
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(20);
                    setEnemyX(getEnemyX()+2);
                    getEnemyImage().setX(getEnemyX());
                    checkPlayerHitBox();
                    gravityOnEnemy();
                }
                for(double i = 0; i < getOneMovementSite(); i += 0.2){
                    Thread.sleep(20);
                    setEnemyX(getEnemyX()-2);
                    getEnemyImage().setX(getEnemyX());
                    checkPlayerHitBox();
                    gravityOnEnemy();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void gravityOnEnemy(){
        isBlockUnderIt = false;

        enemyVelY += enemyGravity;
        double newYPosition = getEnemyImage().getY() + enemyVelY;

        int blockY = (int) ((newYPosition + getEnemyImage().getFitHeight()) / getTileSize());

        for (int i = 0; i < getEnemySize(); i++) {
            int blockX = (int) ((getEnemyImage().getX() + i) / getTileSize());

            if (blockX >= 0 && blockX < map[0].length) {
                if (blockY >= 0 && blockY < map.length && isBlockSolid(map[blockY][blockX])) {
                    isBlockUnderIt = true;
                    break;
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

    public int getEnemyVelY() {
        return enemyVelY;
    }

    public void setEnemyVelY(int enemyVelY) {
        this.enemyVelY = enemyVelY;
    }
}
