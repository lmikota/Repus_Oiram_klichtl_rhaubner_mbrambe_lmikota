package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tilemap {
    public final int TILE_SIZE = 40;
    public final int ROWS = 10;
    public final int COLS = 59;
    private int[][] tileMapPattern;
    private Pane tyleMapPane;

    public Tilemap(int[][] tileMapPattern) {
        setTileMapPattern(tileMapPattern);
        drawTileMap();
    }

    public void drawTileMap() {
        tyleMapPane = new Pane();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int tileType = tileMapPattern[row][col];

                Rectangle tile = new Rectangle(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);

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
}