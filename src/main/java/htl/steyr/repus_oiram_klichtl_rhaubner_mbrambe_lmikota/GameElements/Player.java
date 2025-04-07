package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;

public class Player implements Runnable {
    private ImageView playerImage;
    private double playerSize;

    private double playerX;
    private double playerY;
    private double playerVelY = 0;
    private boolean isJumping = false;
    private double tileSize;
    boolean isBlockUnderIt = false;
    boolean isBlockOverIt = false;
    private int hp = 3;
    private boolean safeTime = false;
    private boolean plinkHigh = false;

    private final double JUMP_SPEED = -15;
    private final double MOVE_SPEED = 4;

    @Override
    public void run() {
        setSafeTime(true);

        LocalTime start = LocalTime.now();
        LocalTime end;
        Duration duration;
        double deltaTime;

        do {
            end = LocalTime.now();
            duration = Duration.between(start, end);
            deltaTime = duration.toSeconds();

            if(isPlinkHigh()){
                getPlayerImage().setOpacity(0.75);
                setPlinkHigh(false);
            }else{
                getPlayerImage().setOpacity(0.25);
                setPlinkHigh(true);
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } while (deltaTime < 0.5);

        setSafeTime(false);
        getPlayerImage().setOpacity(1.0);
    }

    public Player(Image playerImage, double playerSize, double tileSize) {
        setPlayerImage(playerImage);
        setPlayerSize(playerSize);
        changePlayerSize(getPlayerSize());
        setTileSize(tileSize);
        setPlayerSpawn(0, 0);
    }

    public void setPlayerSpawn(int x, int y) {
        playerX = x;
        playerY = y;
        getPlayerImage().setX(playerX);
        getPlayerImage().setY(playerY);
    }

    public void changePlayerSize(double playerSize) {
        playerImage.setFitHeight(playerSize);
        playerImage.setFitWidth(playerSize);
    }

    public void playerMovementY(int map[][], Set<KeyCode> pressedKeys, double GRAVITY) {
        if (pressedKeys.contains(KeyCode.SPACE) && !isJumping && isBlockUnderIt) {
            playerVelY = JUMP_SPEED;
            isJumping = true;
        }

        playerVelY += GRAVITY;
        double newYPosition = getPlayerImage().getY() + playerVelY;

        int blockYAbovePlayer = (int) (newYPosition / getTileSize());
        int blockY = (int) ((newYPosition + getPlayerImage().getFitHeight()) / getTileSize());

        for (int i = 0; i < getPlayerSize(); i++) {
            int blockX = (int) ((getPlayerImage().getX() + i) / getTileSize());

            if (blockX >= 0 && blockX < map[0].length) {
                if (blockYAbovePlayer >= 0 && blockYAbovePlayer < map.length && isBlockSolid(map[blockYAbovePlayer][blockX])) {
                    isBlockOverIt = true;
                    break;
                } else if (blockY >= 0 && blockY < map.length && isBlockSolid(map[blockY][blockX])) {
                    isBlockUnderIt = true;
                    break;
                }else{
                    isBlockUnderIt = false;
                    isBlockOverIt = false;
                }
            }
        }

        if (isBlockUnderIt && !isBlockOverIt) {
            getPlayerImage().setY(blockY * getTileSize() - getPlayerImage().getFitHeight());
            playerVelY = 0;
            isJumping = false;
        } else if (isBlockOverIt) {
            getPlayerImage().setY(blockYAbovePlayer * getTileSize() + getTileSize());
            playerVelY = 0;
        } else {
            getPlayerImage().setY(newYPosition);
        }
    }

    public void playerMovementX(Set<KeyCode> pressedKeys, int map[][]) {
        if (pressedKeys.contains(KeyCode.D)) {
            if (!checkCollisionX(playerX + MOVE_SPEED, map)) {
                playerX += MOVE_SPEED;
                getPlayerImage().setX(playerX);
            }
        } else if (pressedKeys.contains(KeyCode.A)) {
            if (!checkCollisionX(playerX - MOVE_SPEED, map)) {
                playerX -= MOVE_SPEED;
                getPlayerImage().setX(playerX);
            }
        }
    }

    public boolean checkCollisionX(double futurePlayerX, int map[][]) {
        int topTileY = (int) (playerImage.getY() / getTileSize());
        int bottomTileY = (int) ((playerImage.getY() + playerImage.getFitHeight() - 1) / getTileSize());

        if (topTileY < 0) topTileY = 0;
        if (bottomTileY >= map.length) bottomTileY = map.length - 1;

        if (futurePlayerX > playerX) {
            int rightTileX = (int) ((futurePlayerX + playerImage.getFitWidth()) / getTileSize());

            for (int y = topTileY; y <= bottomTileY; y++) {
                if (rightTileX < map[0].length && isBlockSolid(map[y][rightTileX])) {
                    return true;
                }
            }
        } else {
            int leftTileX = (int) (futurePlayerX / getTileSize());

            for (int y = topTileY; y <= bottomTileY; y++) {
                if (leftTileX >= 0 && isBlockSolid(map[y][leftTileX])) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isBlockSolid(int block) {
        return block == 1 || block == 2 || block == 3 || block == 4 || block == 5 || block == 6 || block == 7 || block == 187;
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = new ImageView(playerImage);
        getPlayerImage().setFitWidth(getPlayerSize());
        getPlayerImage().setFitHeight(getPlayerSize());
        getPlayerImage().minWidth(getPlayerSize());
        getPlayerImage().minHeight(getPlayerSize());
        getPlayerImage().maxHeight(getPlayerSize());
        getPlayerImage().maxWidth(getPlayerSize());
    }

    public double getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(double playerSize) {
        this.playerSize = playerSize;
    }

    public double getTileSize() {
        return tileSize;
    }

    public void setTileSize(double tileSize) {
        this.tileSize = tileSize;
    }

    public double getPlayerVelY() {
        return playerVelY;
    }

    public void setPlayerVelY(double playerVelY) {
        this.playerVelY = playerVelY;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isSafeTime() {
        return safeTime;
    }

    public void setSafeTime(boolean safeTime) {
        this.safeTime = safeTime;
    }

    public boolean isPlinkHigh() {
        return plinkHigh;
    }

    public void setPlinkHigh(boolean plinkHigh) {
        this.plinkHigh = plinkHigh;
    }
}