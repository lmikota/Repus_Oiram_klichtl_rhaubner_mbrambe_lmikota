package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import javafx.application.Platform;
import javafx.scene.image.Image;

public class FloorEnemy extends Enemy implements Runnable {
    private int map[][];
    private int enemyVelY = 0;
    private boolean isBlockUnderIt;
    private double enemyGravity = 0.5;

    /**
     * FloorEnemy erbt von der Enemy Klasse
     * Dieser Gegener läuft am boden nach links und rechts
     * zusätzlich kann dieser von platformen runterfallen
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
    public FloorEnemy(Image enemyImage, int enemySize, int tileSize, double enemyMovementX, Player player, int map[][], double xCord, double yCord) {
        super(enemyImage, enemySize, tileSize, enemyMovementX, player, xCord, yCord);
        setMap(map);
    }

    /**
     * Diese run funktion simuliert die bewegung des Gegners.
     * Der Gegner läuft solange er noch am Leben ist bzw solagne die
     * isDead variable false ist.
     * Die zwei for schleife simulieren einmal die bewegung nach linkse
     * und anderseits die bewegung nach rechts.
     * bei jeden durchlauf der for schleife wird die checkPlayerHitBox()
     * funktion ausgefürht um zu checken ob der spieler den gegner berührt
     * die gravityOnEnemy() funktion ist dafür zuständig den Gegner auf den Boden zu ziehen
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
     * der Enemy wird wie der Spieler auf den boden gezogen.
     * hierführ wird überprüft ob die box unter dem gegener ein fester boden ist.
     * Wenn dies nicht dem fall entspricht, dann wirkt eine anziehungs kraft auf ihm
     * die nach unten zieht.
     */
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

    /**
     * alle blöcke die in der map als fester boden definiert sind
     *
     * @param block
     * @return
     */
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
