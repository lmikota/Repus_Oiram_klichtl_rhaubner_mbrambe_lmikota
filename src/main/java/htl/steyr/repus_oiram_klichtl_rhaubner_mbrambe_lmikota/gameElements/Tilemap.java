package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Tilemap {
    public final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int ROWS = 18;
    private final double TILE_SIZE = SCREEN_HEIGHT / ROWS;
    private int cols;
    private int[][] tileMapPattern;
    private Pane tyleMapPane;
    final String DIRT_IMAGE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/3_dirtblock.png";
    final String GRASS_IMAGE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/4_grassblock.png";
    final String STONE_DIRT_BLOCK = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/1_stonedirtblock.png";
    final String STONE_DIRT_TRANSITION = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/2_stoneDirtTransition.png";
    final String FLOATING_GRASS_LEFT = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/5_floatingGrassblockLeft.png";
    final String FLOATING_GRASS_RIGHT = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/7_floatingGrassblockRight.png";
    final String FLOATING_GRASS_MIDDLE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/6_floatingGrassBlockMiddle.png";
    final String TREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/11_Tree.png";
    final String TOWER_LEFT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/TurmLinks1.png";
    final String TOWER_LEFT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/TurmLinks2.png";
    final String TOWER_LEFT_THREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/TurmLinks3.png";
    final String TOWER_LEFT_FOUR = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/TurmLinks4.png";
    final String TOWER_RIGHT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/turmRechts1.png";
    final String TOWER_RIGHT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/turmRechts2.png";
    final String TOWER_RIGHT_THREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/turmRechts3.png";
    final String TOWER_RIGHT_FOUR = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/turmRechts4.png";
    final String TREE_RIGHT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/baumRechts1.png";
    final String TREE_RIGHT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/baumRechts2.png";
    final String TREE_LEFT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/bauLlinks1.png";
    final String TREE_LEFT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/baumLinks2.png";


    private SuperTrank superTrank;

    public Tilemap(int[][] tileMapPattern) {
        setTileMapPattern(tileMapPattern);
        setCols(tileMapPattern[0].length);
        drawTileMap();
    }

    public void drawTileMap() {
        tyleMapPane = new Pane();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < cols; col++) {
                int tileType = tileMapPattern[row][col];
                ImageView imageView = new ImageView();
                imageView.setFitWidth(TILE_SIZE);
                imageView.setFitHeight(TILE_SIZE);
                imageView.setX(col * TILE_SIZE);
                imageView.setY(row * TILE_SIZE);
                imageView.maxHeight(TILE_SIZE);
                imageView.minHeight(TILE_SIZE);
                imageView.maxWidth(TILE_SIZE);
                imageView.minWidth(TILE_SIZE);

                switch (tileType) {
                    /**
                     * @ToDo
                     * Item allignment is centered
                     */
                    case -1:
                        break;
                    case 0:
                        break;
                    case 1:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getSTONE_DIRT_BLOCK())));
                        break;
                    case 2:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getSTONE_DIRT_TRANSITION())));
                        break;
                    case 3:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getDIRT_IMAGE())));
                        break;
                    case 4:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getGRASS_IMAGE())));
                        break;
                    case 5:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getFLOATING_GRASS_LEFT())));
                        break;
                    case 6:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getFLOATING_GRASS_MIDDLE())));
                        break;
                    case 7:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getFLOATING_GRASS_RIGHT())));
                        break;
                    case 187:
                        // invisible Border
                        break;
                    case 11:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTREE())));
                        break;
                    case 12:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_LEFT_ONE())));
                        break;
                    case 13:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_RIGHT_ONE())));
                        break;
                    case 14:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_LEFT_TWO())));
                        break;
                    case 15:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_RIGHT_TWO())));
                        break;
                    case 16:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_LEFT_THREE())));
                        break;
                    case 17:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_RIGHT_THREE())));
                        break;
                    case 18:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_LEFT_FOUR())));
                        break;
                    case 19:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTOWER_RIGHT_FOUR())));
                        break;
                    case 20:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTREE_LEFT_ONE())));
                        break;
                    case 21:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTREE_RIGHT_ONE())));
                        break;
                    case 22:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTREE_LEFT_TWO())));
                        break;
                    case 23:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getTREE_RIGHT_TWO())));
                        break;
                }


                tyleMapPane.getChildren().add(imageView);
            }
        }
    }

    public double getTileMapLengthInPixel() {
        return getTileMapPattern()[0].length * getTILE_SIZE() + getTileMapPattern().length * getTILE_SIZE();
    }


    public int[][] getTileMapPattern() {
        return tileMapPattern;
    }

    public void setTileMapPattern(int[][] tileMapPattern) {
        this.tileMapPattern = tileMapPattern;
    }

    public Pane getTyleMapPane() {
        return tyleMapPane;
    }

    public void setTyleMapPane(Pane tyleMapPane) {
        this.tyleMapPane = tyleMapPane;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getROWS() {
        return ROWS;
    }

    public double getTILE_SIZE() {
        return TILE_SIZE;
    }

    public String getDIRT_IMAGE() {
        return DIRT_IMAGE;
    }

    public String getGRASS_IMAGE() {
        return GRASS_IMAGE;
    }

    public String getSTONE_DIRT_BLOCK() {
        return STONE_DIRT_BLOCK;
    }

    public String getFLOATING_GRASS_MIDDLE() {
        return FLOATING_GRASS_MIDDLE;
    }

    public String getFLOATING_GRASS_RIGHT() {
        return FLOATING_GRASS_RIGHT;
    }

    public String getFLOATING_GRASS_LEFT() {
        return FLOATING_GRASS_LEFT;
    }

    public String getSTONE_DIRT_TRANSITION() {
        return STONE_DIRT_TRANSITION;
    }

    public String getTREE() {
        return TREE;
    }

    public String getTOWER_LEFT_ONE() {
        return TOWER_LEFT_ONE;
    }

    public String getTOWER_LEFT_TWO() {
        return TOWER_LEFT_TWO;
    }

    public String getTOWER_LEFT_FOUR() {
        return TOWER_LEFT_FOUR;
    }

    public String getTOWER_LEFT_THREE() {
        return TOWER_LEFT_THREE;
    }

    public String getTOWER_RIGHT_ONE() {
        return TOWER_RIGHT_ONE;
    }

    public String getTOWER_RIGHT_TWO() {
        return TOWER_RIGHT_TWO;
    }

    public String getTOWER_RIGHT_THREE() {
        return TOWER_RIGHT_THREE;
    }

    public String getTOWER_RIGHT_FOUR() {
        return TOWER_RIGHT_FOUR;
    }

    public String getTREE_LEFT_TWO() {
        return TREE_LEFT_TWO;
    }

    public String getTREE_LEFT_ONE() {
        return TREE_LEFT_ONE;
    }

    public String getTREE_RIGHT_ONE() {
        return TREE_RIGHT_ONE;
    }

    public String getTREE_RIGHT_TWO() {
        return TREE_RIGHT_TWO;
    }
}