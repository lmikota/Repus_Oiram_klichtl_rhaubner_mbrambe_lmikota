package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.application.Platform;
import javafx.scene.image.Image;

import java.time.Duration;
import java.time.LocalTime;

public class JumpingEnemy extends Enemy implements Runnable {
    private int map[][];
    private double enemyVelY = 0;
    private boolean isBlockUnderIt = false;
    private double enemyGravity = 0.5;

    private LocalTime lastJump;
    private int jumpSpeed = -10;

    /**
     * JumpingEnemy erbt von der Enemy Klasse
     * dieser Gegner springt zufällignach oben und wird auch wie
     * der FloorEnemy auf den boden gezogen
     *
     * @param enemyImage
     * @param enemySize
     * @param tileSize
     * @param enemyMovementX
     * @param player
     * @param map
     * @param xCord
     * @param yCord
     */
    public JumpingEnemy(Image enemyImage, int enemySize, int tileSize, double enemyMovementX, Player player, int map[][], double xCord, double yCord) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player, xCord, yCord);
        this.map = map;
        lastJump = LocalTime.now();
    }

    /**
     * Diese run funktion simuliert die bewegung des Gegners.
     * Der Gegner läuft solange er noch am Leben ist bzw solagne die
     * isDead variable false ist.
     * Die zwei for schleife simulieren einmal die bewegung nach linkse
     * und anderseits die bewegung nach rechts.
     * bei jeden durchlauf der for schleife wird die checkPlayerHitBox()
     * funktion ausgefürht um zu checken ob der spieler den gegner berührt.
     * in der gravityOnEnemy() funktion wird die schwerkraft und das zufällige
     * springen des gegners simuliert
     */
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

                    Thread.sleep(15);
                    setEnemyX(getEnemyX()+2);

                    Platform.runLater(() -> {
                        getEnemyImage().setX(getEnemyX());
                    });

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

                    Thread.sleep(15);
                    setEnemyX(getEnemyX()-2);

                    Platform.runLater(() -> {
                        getEnemyImage().setX(getEnemyX());
                    });

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

    /**
     * Diese Methode überpüft ob eine feste box unter dem Gegner ist.
     * Zudem ist diese methode dafür zuständig den Gegner in die höhe springen zu lassen
     */
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
