package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class Enemy{
    private ImageView enemyImage;
    private int enemySize;

    private double enemyX;
    private double enemyY;
    private int tileSize;
    private double enemyMovementX;
    private double oneMovementSite;

    private boolean playerTouching;
    private Player player;

    private boolean dead = false;

    public Enemy(Image enemyImage, int enemySize, int tileSize, double enemyMovementX, Player player, double xCord, double yCord) {
        setEnemySize(enemySize);
        setEnemyImage(enemyImage);
        setTileSize(tileSize);
        setEnemySpawn(xCord,yCord);
        setEnemyMovementX(enemyMovementX);
        setOneMovementSite(getEnemyMovementX()/2);
        setPlayer(player);
    }

    public void checkPlayerHitBox() {
        if (isDead()) return;

        ImageView playerImage = player.getPlayerImage();
        ImageView enemyImage = getEnemyImage();

        double playerX = playerImage.getX();
        double playerY = playerImage.getY();
        double playerWidth = playerImage.getFitWidth();
        double playerHeight = playerImage.getFitHeight();

        double enemyX = enemyImage.getX(); // FIXED
        double enemyY = enemyImage.getY(); // FIXED
        double enemyWidth = enemyImage.getFitWidth();
        double enemyHeight = enemyImage.getFitHeight();

        boolean xOverlap = playerX + playerWidth > enemyX && playerX < enemyX + enemyWidth;
        boolean yOverlap = playerY + playerHeight > enemyY && playerY < enemyY + enemyHeight;

        if (xOverlap && yOverlap) {
            double playerBottom = playerY + playerHeight;
            double enemyTop = enemyY;

            // präziser Stomp-Check:
            boolean isStomp = player.getPlayerVelY() > 0 && playerBottom > enemyTop && playerBottom < enemyTop + enemyHeight / 2;

            if (isStomp) {
                playerKillsEnemy();
                player.setPlayerVelY(-5);
                MusicPlayer.getInstance().playSound("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/WAV/enemyDeathSound.wav");
            } else if (!player.isSafeTime() && !player.isCapeEffect()) {
                onEnemyHitsPlayer();
            }
        }
    }


    public void playerKillsEnemy() {
        setDead(true);
    }

    public void onEnemyHitsPlayer(){
        if(!playerTouching){
            getPlayer().setHp(getPlayer().getHp()-1);
            getPlayer().getGameplayApplication().setHeartImagesBasedOnHp();
            if(getPlayer().getHp() == 0){
                System.out.println("kein leben mehr");
                getPlayer().onPlayerDead();
                String deathSound = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/WAV/deathSound.wav";
                MusicPlayer.getInstance().playSound(deathSound);
            }else{
                Thread startSafeTime = new Thread(getPlayer());
                startSafeTime.start();
            }
        }
    }

    public void setEnemySpawn(double x, double y) {
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

    public double getEnemyMovementX() {
        return enemyMovementX;
    }

    public void setEnemyMovementX(double enemyMovementX) {
        this.enemyMovementX = enemyMovementX;
    }

    public double getOneMovementSite() {
        return oneMovementSite;
    }

    public void setOneMovementSite(double oneMovementSite) {
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
