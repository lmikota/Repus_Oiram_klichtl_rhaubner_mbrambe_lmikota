package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Player {
    private ImageView playerImage;
    private int playerSize;

    private double playerX = 100;
    private double playerY = 400 - 200;
    private double playerVelY = 0;
    private boolean isJumping = false;
    private int tileSize;
    boolean isBlockUnderIt = false;
    boolean isBlockOverIt = false;

    private final double JUMP_SPEED = -15;
    private final double MOVE_SPEED = 3;

    public Player(Image playerImage, int playerSize, int tileSize) {
        setPlayerImage(playerImage);
        setPlayerSize(playerSize);
        changePlayerSize(this.playerSize);
        setTileSize(tileSize);
        setPlayerSpawn(100,100);
    }

    public void setPlayerSpawn(int x, int y) {
        playerX = x;
        playerY = y;
        getPlayerImage().setX(playerX);
        getPlayerImage().setY(playerY);
    }

    public void changePlayerSize(int playerSize) {
        playerImage.setFitHeight(playerSize);
        playerImage.setFitWidth(playerSize);
    }

    public void playerMovementY(int map[][], Set<KeyCode> pressedKeys, double GRAVITY) {
        if (pressedKeys.contains(KeyCode.SPACE) && !isJumping) {
            playerVelY = JUMP_SPEED;
            isJumping = true;
        }

        playerVelY += GRAVITY;
        double newYPosition = getPlayerImage().getY() + playerVelY;

        int blockYAbovePlayer = (int) ((newYPosition - getPlayerImage().getFitHeight()) / getTileSize());
        int blockY = (int) ((newYPosition + getPlayerImage().getFitHeight()) / getTileSize());

        for(int i = -(getPlayerSize()/2); i<= (getPlayerSize()/2); i++){
            int blockX = (int)  ((getPlayerImage().getX() + i) / getTileSize());

            if (blockYAbovePlayer >= 0 && map[blockYAbovePlayer][blockX] == 2) {
                isBlockOverIt = true;
            } else if (blockY < map.length && map[blockY][blockX] == 2) {
                isBlockUnderIt = true;
                break;
            }
        }

        if (isBlockUnderIt && !isBlockOverIt) {
            getPlayerImage().setY(blockY * getTileSize() - getPlayerImage().getFitHeight());
            playerVelY = 0;
            isJumping = false;
        } else if (isBlockOverIt) {
            getPlayerImage().setY(blockYAbovePlayer * getTileSize() + getTileSize());
        } else {
            getPlayerImage().setY(newYPosition);
        }

    }

    public void playerMovementX(Set<KeyCode> pressedKeys, int map[][]) {
        if(pressedKeys.contains(KeyCode.D)) {
            if(!checkNextboxX(playerX += MOVE_SPEED,map)) {
                playerX += MOVE_SPEED;
                getPlayerImage().setX(playerX);
            }else{
                playerX -= MOVE_SPEED;
            }
        }else if(pressedKeys.contains(KeyCode.A)) {
            if(!checkNextboxX(playerX -= MOVE_SPEED,map)) {
                playerX -= MOVE_SPEED;
                getPlayerImage().setX(playerX);
            }else{
                playerX += MOVE_SPEED;
            }
        }
    }

    public boolean checkNextboxX(double futurePlayerX, int map[][]) {
        int blockXLeft = (int)  ((futurePlayerX + getPlayerImage().getFitWidth()) / getTileSize());
        int blockY = (int) (playerImage.getY()/ getTileSize());
        int blockXRight = blockXLeft - 1;

        if(map[blockY][blockXLeft] == 3 || map[blockY][blockXLeft] == 2) {
            return true;
        }else if(map[blockY][blockXRight] == 3 || map[blockY][blockXRight] == 2) {
             return true;
        }else{
            return false;
        }
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = new ImageView(playerImage);
    }

    public int getPlayerSize() {
        return playerSize;
    }

    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }
}
