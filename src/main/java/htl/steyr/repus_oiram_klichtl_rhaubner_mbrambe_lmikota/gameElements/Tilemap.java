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
    private final int TILE_SIZE = SCREEN_HEIGHT / ROWS;
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

                Rectangle tile = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                switch (tileType) {
                    case 0:
                        tile.setFill(Color.TRANSPARENT);
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
                }

                if (tileType != 0) {
                    tile.setStroke(Color.BLACK);
                }
                tyleMapPane.getChildren().add(imageView);
            }
        }
    }

    public int getTileMapLengthInPixel() {
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

    public int getTILE_SIZE() {
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


}