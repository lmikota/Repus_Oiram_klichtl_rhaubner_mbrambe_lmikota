package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;

public class Enemy{
    private ImageView enemyImage;
    private int enemySize;

    private double enemyX;
    private double enemyY;
    private int tileSize;
    private int enemyMovementX;
    private int oneMovementSite;

    private boolean playerTouching;
    private Player player;

    private boolean dead = false;

    public Enemy(Image enemyImage, int enemySize, int tileSize, int enemyMovementX, Player player) {
        setEnemySize(enemySize);
        setEnemyImage(enemyImage);
        setTileSize(tileSize);
        setEnemySpawn(200,400);
        setEnemyMovementX(enemyMovementX);
        setOneMovementSite(getEnemyMovementX()/2);
        setPlayer(player);
    }

    public void checkPlayerHitBox() {
        double playerX = getPlayer().getPlayerImage().getX();
        double playerY = getPlayer().getPlayerImage().getY();

        for(int i = -getTileSize()/2; i < getTileSize()/2; i += 1){
            for(int j = -getTileSize()/2; j < getTileSize()/2; j += 1){
                if(playerX == enemyX+j && playerY == enemyY+i){
                    player.setPlayerVelY(0);

                    if(playerY < enemyY + 6){
                        playerKillsEnemy();
                    }

                    if(!getPlayer().isSafeTime()){
                        onEnemyHitsPlayer();
                    }
                    setPlayerTouching(true);
                }else{
                    setPlayerTouching(false);
                }
            }
        }
    }

    public void playerKillsEnemy() {
        System.out.println("Gegner wurde getroffen!");
        setDead(true);
        System.out.println("isDead: " + isDead());
    }



    public void onEnemyHitsPlayer(){
        if(!playerTouching){
            getPlayer().setHp(getPlayer().getHp()-1);
            if(getPlayer().getHp() == 0){
                System.out.println("tot");
                javafx. application. Platform. exit();
            }else{
                Thread startSafeTime = new Thread(getPlayer());
                startSafeTime.start();
            }
        }
    }

    public void setEnemySpawn(int x, int y) {
        enemyY = y;
        enemyX = x;
        getEnemyImage().setX(x);
        getEnemyImage().setY(y);
    }

    public ImageView getEnemyImage() {
        return enemyImage;
    }

    public void setEnemyImage(Image enemyImage) {
        this.enemyImage = new ImageView(enemyImage);
        getEnemyImage().setFitWidth(getEnemySize());
        getEnemyImage().setFitHeight(getEnemySize());
        getEnemyImage().minHeight(getEnemySize());
        getEnemyImage().minWidth(getEnemySize());
        getEnemyImage().maxHeight(getEnemySize());
        getEnemyImage().maxWidth(getEnemySize());
    }

    public int getEnemySize() {
        return enemySize;
    }

    public void setEnemySize(int enemySize) {
        this.enemySize = enemySize;
    }

    public double getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(double enemyX) {
        this.enemyX = enemyX;
    }

    public double getEnemyY() {
        return enemyY;
    }

    public void setEnemyY(double enemyY) {
        this.enemyY = enemyY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public int getEnemyMovementX() {
        return enemyMovementX;
    }

    public void setEnemyMovementX(int enemyMovementX) {
        this.enemyMovementX = enemyMovementX;
    }

    public int getOneMovementSite() {
        return oneMovementSite;
    }

    public void setOneMovementSite(int oneMovementSite) {
        this.oneMovementSite = oneMovementSite;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isPlayerTouching() {
        return playerTouching;
    }

    public void setPlayerTouching(boolean playerTouching) {
        this.playerTouching = playerTouching;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
