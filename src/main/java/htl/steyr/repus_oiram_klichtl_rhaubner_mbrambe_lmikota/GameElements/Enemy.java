package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

import java.util.Objects;

public class Enemy {
    private ImageView enemyImage;
    private int enemySize;

    private double enemyX;
    private double enemyY;
    private int tileSize;
    private double enemyMovementX;
    private double oneMovementSite;

    private Player player;

    private boolean dead = false;

    /**
     * Constructor of the Enemy class.
     *
     * @param enemyImage
     * @param enemySize
     * @param tileSize
     * @param enemyMovementX
     * @param player
     * @param xCord
     * @param yCord
     */

    public Enemy(Image enemyImage, int enemySize, int tileSize, double enemyMovementX, Player player, double xCord, double yCord) {
        setEnemySize(enemySize);
        setEnemyImage(enemyImage);
        setTileSize(tileSize);
        setEnemySpawn(xCord, yCord);
        setEnemyMovementX(enemyMovementX);
        setOneMovementSite(getEnemyMovementX() / 2);
        setPlayer(player);
    }

    /**
     * This function checks if the enemy image overlaps with the player image.
     * If the player approaches from above, they defeat the enemy; otherwise,
     * the player loses one life.
     */

    public void checkPlayerHitBox() {
        if (isDead()) return;

        ImageView playerImage = player.getPlayerImage();
        ImageView enemyImage = getEnemyImage();

        double playerX = playerImage.getX();
        double playerY = playerImage.getY();
        double playerWidth = playerImage.getFitWidth();
        double playerHeight = playerImage.getFitHeight();

        double enemyX = enemyImage.getX();
        double enemyY = enemyImage.getY();
        double enemyWidth = enemyImage.getFitWidth();
        double enemyHeight = enemyImage.getFitHeight();

        boolean xOverlap = playerX + playerWidth > enemyX && playerX < enemyX + enemyWidth;
        boolean yOverlap = playerY + playerHeight > enemyY && playerY < enemyY + enemyHeight;

        if (xOverlap && yOverlap) {
            double playerBottom = playerY + playerHeight;
            double enemyTop = enemyY;

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

    /**
     * Defines the enemy object as dead.
     */

    public void playerKillsEnemy() {
        setDead(true);
    }

    /**
     * This function is executed when the enemy touches the player.
     * The player loses one life.
     * Then, it checks if the player has more than one life remaining.
     * If not, the player dies and the game ends.
     * Otherwise, the player enters a safe time where they cannot take damage.
     */

    public void onEnemyHitsPlayer() {
        getPlayer().setHp(getPlayer().getHp() - 1);
        getPlayer().getGameplayApplication().setHeartImagesBasedOnHp();
        if (getPlayer().getHp() == 0) {
            System.out.println("kein leben mehr");
            getPlayer().onPlayerDead();
            String deathSound = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/WAV/deathSound.wav";
            MusicPlayer.getInstance().playSound(deathSound);
        } else {
            Thread startSafeTime = new Thread(getPlayer());
            startSafeTime.start();
        }
    }

    /**
     * At these coordinates, the enemy is placed in the game.
     * @param x
     * @param y
     */

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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
