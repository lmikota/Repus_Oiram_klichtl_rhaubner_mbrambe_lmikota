package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Tilemap {
    public final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int ROWS = 18;
    private final double TILE_SIZE = SCREEN_HEIGHT / ROWS;
    private int cols;
    private int[][] tileMapPattern;
    private MapDataReader reader;
    private Pane tyleMapPane;
    final String DIRT_IMAGE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/3_dirtblock.png";
    final String GRASS_IMAGE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/4_grassblock.png";
    final String STONE_DIRT_BLOCK = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/1_stonedirtblock.png";
    final String STONE_DIRT_TRANSITION = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/2_stoneDirtTransition.png";
    final String FLOATING_GRASS_LEFT = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/5_floatingGrassblockLeft.png";
    final String FLOATING_GRASS_RIGHT = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/7_floatingGrassblockRight.png";
    final String FLOATING_GRASS_MIDDLE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/6_floatingGrassBlockMiddle.png";
    final String TREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/11_Tree.png";
    final String TOWER_LEFT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/TurmLinks1.png";
    final String TOWER_LEFT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/TurmLinks2.png";
    final String TOWER_LEFT_THREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/TurmLinks3.png";
    final String TOWER_LEFT_FOUR = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/TurmLinks4.png";
    final String TOWER_RIGHT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/turmRechts1.png";
    final String TOWER_RIGHT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/turmRechts2.png";
    final String TOWER_RIGHT_THREE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/turmRechts3.png";
    final String TOWER_RIGHT_FOUR = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/turmRechts4.png";
    final String TREE_RIGHT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/baumRechts1.png";
    final String TREE_RIGHT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/baumRechts2.png";
    final String TREE_LEFT_ONE = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/bauLlinks1.png";
    final String TREE_LEFT_TWO = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/IMG/BlockElements/baumLinks2.png";


    private SuperTrank superTrank;

    public Tilemap(int[][] tileMapPattern) {
        setTileMapPattern(tileMapPattern);
        setCols(tileMapPattern[0].length);
        drawTileMap();
    }

    public void drawTileMap() {
        tyleMapPane = new Pane();
        {
            try {
                reader = new MapDataReader();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                     * (W)Rapper Klasse implementieren!!!!!!!
                     */
                    case -1:
                        break;
                    case 0:
                        break;
                    case 1:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(1)))));
                        break;
                    case 2:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(2)))));
                        break;
                    case 3:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(3)))));
                        break;
                    case 4:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(4)))));
                        break;
                    case 5:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(5)))));
                        break;
                    case 6:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(6)))));
                        break;
                    case 7:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(7)))));
                        break;
                    case 187:
                        // invisible Border
                        break;
                    case 11:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(11)))));
                        break;
                    case 12:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(12)))));
                        break;
                    case 13:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(13)))));
                        break;
                    case 14:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(14)))));
                        break;
                    case 15:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(15)))));
                        break;
                    case 16:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(16)))));
                        break;
                    case 17:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(17)))));
                        break;
                    case 18:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(18)))));
                        break;
                    case 19:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(19)))));
                        break;
                    case 20:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(20)))));
                        break;
                    case 21:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(21)))));
                        break;
                    case 22:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(22)))));
                        break;
                    case 23:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(23)))));
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