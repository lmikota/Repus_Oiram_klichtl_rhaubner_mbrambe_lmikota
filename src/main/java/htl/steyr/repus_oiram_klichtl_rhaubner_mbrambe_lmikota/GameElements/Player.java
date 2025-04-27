package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Audio.MusicPlayer;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public class Player implements Runnable {
    private ImageView playerImage;
    private Image[] goingright;
    private Image[] goingleft;
    private double playerSize;
    private Image standing;

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
    private boolean CapeEffect = false;

    private double JUMP_SPEED = -15;
    private final double MOVE_SPEED = 4;
    private boolean moving = false;
    private int currentFrame1 = 0;
    private int currentFrame2 = 0;
    private long lastFrameTime = 0;// in Nanosekunden
    private boolean movingright = false;

    private boolean dead = false;

    public SuperBoots superboots = new SuperBoots(this);
    public SuperTrank superTrank = new SuperTrank(this);
    public SuperUmhang superUmhang = new SuperUmhang(this);

    private GameplayApplication gameplayApplication;

    private LocalTime start;

    /**
     * This run method is responsible for bringing the player into the safe time,
     * during which they cannot be attacked by any enemies.
     * It reduces the player's opacity and uses a loop to check
     * how long the safe time should last.
     */
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

            if (isPlinkHigh()) {
                Platform.runLater(() -> getPlayerImage().setOpacity(0.75));
                setPlinkHigh(false);
            } else {
                Platform.runLater(() -> getPlayerImage().setOpacity(0.25));
                setPlinkHigh(true);
            }

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (deltaTime < 0.5);

        setSafeTime(false);
        Platform.runLater(() -> getPlayerImage().setOpacity(1));
    }

    /**
     * In the constructor, the player's image,
     * size, and the GameApplication class are provided.
     *
     * @param playerImage
     * @param playerSize
     * @param tileSize
     * @param gameplayApplication
     */
    public Player(Image playerImage, double playerSize, double tileSize, GameplayApplication gameplayApplication) {
        setGameplayApplication(gameplayApplication);
        setPlayerImage(playerImage);
        setPlayerSize(playerSize);
        changePlayerSize(getPlayerSize());
        setTileSize(tileSize);
        setPlayerSpawn(0, 0);
        standing =  playerImage;

        goingright = new Image[]{
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-R_1.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-R_2.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-R_3.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-R_4.png"))),
        };

        goingleft = new Image[]{
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-L_1.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-L_2.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-L_3.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/Creatures/anima-L_4.png"))),
        };

        startAnimationTimer();
    }

    private void startAnimationTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateAnimation(now);
            }
        };
        timer.start();
    }

    private void updateAnimation(long now) {
        if (moving) {
            if (now - lastFrameTime > 250_000_000) {
                lastFrameTime = now;
                if (movingright) {
                    getPlayerImage().setImage((goingright[currentFrame1]));

                } else {
                    getPlayerImage().setImage((goingleft[currentFrame2]));
                }
                currentFrame1 = (currentFrame1 + 1) % goingright.length;
                currentFrame2 = (currentFrame2 + 1) % goingleft.length;
            }
        } else {
            getPlayerImage().setImage(standing);
            currentFrame1 = 0;
            currentFrame2 = 0;
        }
    }

    /**
     * At this point, the player is placed in the game.
     * @param x
     * @param y
     */

    public void setPlayerSpawn(int x, int y) {
        playerX = x;
        playerY = y;
        getPlayerImage().setX(playerX);
        getPlayerImage().setY(playerY);
    }

    /**
     * This method allows changing the player's size.
     * The height and width of the player are set to the same value here.
     * @param playerSize
     */

    public void changePlayerSize(double playerSize) {
        playerImage.setFitHeight(playerSize);
        playerImage.setFitWidth(playerSize);
    }

    /**
     * This method checks that the player has not fallen into the void.
     */
    public void checkPlayerLegalHeight() {
        double screenHeight = getTileSize() * 18;
        if (getPlayerImage().getY() > screenHeight) {
            if (!dead) {
                dead = true;
                String fallingIntoVoidSound = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/WAV/fallingIntoVoidSound.wav";
                MusicPlayer.getInstance().playSound(fallingIntoVoidSound);
                onPlayerDead();
            }
        }
    }

    /**
     * This method is executed when the player has completed the level.
     */

    public void onPlayerSuccess() {
        getGameplayApplication().onPlayerWinLevel();
    }

    /**
     * This method is executed when the player dies.
     */

    public void onPlayerDead() {
        getGameplayApplication().onPlayerLoseLevel();
    }

    /**
     * Responsible for checking the player's vertical movement
     * based on their input, including jumping, gravity,
     * and collision with solid blocks.
     *
     * @param map
     * @param pressedKeys
     * @param GRAVITY
     */

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
                } else {
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

    /**
     * Responsible for the player's horizontal movement,
     * including running left and right and stopping when turning.
     *
     * @param pressedKeys
     * @param map
     */
    public void playerMovementX(Set<KeyCode> pressedKeys, int map[][]) {
        if (pressedKeys.contains(KeyCode.D)) {
            if (!checkCollisionX(playerX + MOVE_SPEED, map)) {
                playerX += MOVE_SPEED;
                getPlayerImage().setX(playerX);
                movingright = true;
                moving = true;
            }
        } else if (pressedKeys.contains(KeyCode.A)) {
            if (!checkCollisionX(playerX - MOVE_SPEED, map)) {
                playerX -= MOVE_SPEED;
                getPlayerImage().setX(playerX);
                movingright = false;
                moving = true;
            }
        } else {
            moving = false;
        }
    }

    /**
     * Checks if the block in the tile map,
     * that the player is moving into, is a solid block.
     *
     * @param futurePlayerX
     * @param map
     * @return
     */

    public boolean checkCollisionX(double futurePlayerX, int map[][]) {
        int topTileY = (int) (playerImage.getY() / getTileSize());
        int bottomTileY = (int) ((playerImage.getY() + playerImage.getFitHeight() - 1) / getTileSize());

        if (topTileY < 0) topTileY = 0;
        if (bottomTileY >= map.length) bottomTileY = map.length - 1;

        if (futurePlayerX > playerX) {
            int rightTileX = (int) ((futurePlayerX + playerImage.getFitWidth()) / getTileSize());

            for (int y = topTileY; y <= bottomTileY; y++) {
                if(isItemBlock(map[y][rightTileX])) {
                    activatedItem(map[y][rightTileX], y, rightTileX);
                }else{
                    if (rightTileX < map[0].length && isBlockSolid(map[y][rightTileX])) {
                        if (rightTileX < map[0].length && isWinningBlock(map[y][rightTileX])) {
                            onPlayerSuccess();
                            return true;
                        } else {
                            return true;
                        }
                    }
                }
            }
        } else {
            int leftTileX = (int) (futurePlayerX / getTileSize());

            for (int y = topTileY; y <= bottomTileY; y++) {
                if(isItemBlock(map[y][leftTileX])) {
                    activatedItem(map[y][leftTileX], y, leftTileX);
                }else{
                    if (leftTileX >= 0 && isBlockSolid(map[y][leftTileX])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if the block is solid (with a hitbox).
     */
    public boolean isBlockSolid(int block) {
        return block == 1 || block == 2 || block == 3 || block == 4 || block == 5 || block == 6 || block == 7 || block == 187 || block == 12 || block == 13 || block == 14 || block == 15 || block == 16 || block == 17 || block == 18 || block == 19 ;
    }

    /**
     * Checks if the block is an item.
     * @param block
     * @return
     */
    public boolean isItemBlock(int block){
        return block == -1 || block == -2 || block == -3;
    }

    /**
     * The item is passed through the method,
     * then the item to be activated is selected.
     * The item image is then hidden in the level.
     *
     * @param item
     * @param posX
     * @param posY
     */
    public void activatedItem(int item, int posX, int posY) {
        Point itemKey = new Point(posX, posY);

        if(Tilemap.items.get(itemKey).isVisible()) {
            switch (item) {
                case -1 -> superboots.activateBootsEffect();
                case -2 -> {
                    superTrank.activateTrankEffect();
                    getGameplayApplication().setHeartImagesBasedOnHp();
                }
                case -3 -> superUmhang.activateCapeEffect();
            }
            Tilemap.items.get(itemKey).setVisible(false);
        }
    }

    /**
     * Checks if the blocks belong to the tower.
     * Touching these blocks results in victory.
     *
     * @param block
     * @return
     */
    public boolean isWinningBlock(int block) {
        return block == 12 || block == 13 || block == 14 || block == 15 || block == 16 || block == 17 || block == 18 || block == 19;
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

    public void setJUMP_SPEED(double JUMP_SPEED) {
        this.JUMP_SPEED = JUMP_SPEED;
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

    public boolean isCapeEffect() {
        return CapeEffect;
    }

    public void setCapeEffect(boolean capeEffect) {
        CapeEffect = capeEffect;
    }

    public GameplayApplication getGameplayApplication() {
        return gameplayApplication;
    }

    public void setGameplayApplication(GameplayApplication gameplayApplication) {
        this.gameplayApplication = gameplayApplication;
    }
}