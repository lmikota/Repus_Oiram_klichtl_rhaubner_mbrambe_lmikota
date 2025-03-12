package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tilemap {
    /**
     * @TODO
     * ROWS und COLS sollen Variieren können
     */
    private int tileSize;
    private final int rows = 17;
    private int cols;
    private int[][] tileMapPattern;
    private Pane tyleMapPane;

    public Tilemap(int[][] tileMapPattern, int tileSize) {
        setTileMapPattern(tileMapPattern);
        setTileSize(tileSize);
        setCols(tileMapPattern[0].length);
        drawTileMap();
    }

    public void drawTileMap() {
        tyleMapPane = new Pane();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tileType = tileMapPattern[row][col];

                Rectangle tile = new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);

                switch (tileType) {
                    case 0:
                        tile.setFill(Color.TRANSPARENT);
                        break;
                    case 1:
                        tile.setFill(Color.BROWN);
                        break;
                    case 2:
                        tile.setFill(Color.GREEN);
                        break;
                }

                if (tileType != 0) {
                    tile.setStroke(Color.BLACK);
                }
                tyleMapPane.getChildren().add(tile);
            }
        }
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

    public int getRows() {
        return rows;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

}