package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tilemap {
    /// @ToDo
    /// ROWS und COLS sollen Variieren können
    private int tileSize;
    private final int ROWS = 17;
    private int cols;
    private int[][] tileMapPattern;
    private Pane tyleMapPane;
    final String dirtImageFilePath = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/dirtblock.png";
    final String grassImageFilePath = "/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/img/grassblock.png";

    public Tilemap(int[][] tileMapPattern, int tileSize) {
        setTileMapPattern(tileMapPattern);
        setTileSize(tileSize);
        setCols(tileMapPattern[0].length);
        drawTileMap();
    }

    public void drawTileMap() {
        tyleMapPane = new Pane();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < cols; col++) {
                /// @ToDo
                /// Style CSS für imageview
                int tileType = tileMapPattern[row][col];
                ImageView imageView = new ImageView();
                imageView.setFitWidth(tileSize);
                imageView.setFitHeight(tileSize);
                imageView.setX(col * tileSize);
                imageView.setY(row * tileSize);
                imageView.maxHeight(tileSize);
                imageView.minHeight(tileSize);
                imageView.maxWidth(tileSize);
                imageView.minWidth(tileSize);

                Rectangle tile = new Rectangle(col * tileSize, row * tileSize, tileSize, tileSize);

                switch (tileType) {
                    case 0:
                        tile.setFill(Color.TRANSPARENT);
                        break;
                    case 1:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getDirtImageFilePath())));
                        //tile.setFill(Color.BROWN);
                        break;
                    case 2:
                        imageView.setImage(new Image(getClass().getResourceAsStream(getGrassImageFilePath())));
                        //tile.setFill(Color.GREEN);
                        break;
                }

                if (tileType != 0) {
                    tile.setStroke(Color.BLACK);
                }
                //tyleMapPane.getChildren().add(tile);
                tyleMapPane.getChildren().add(imageView);
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

    public int getROWS() {
        return ROWS;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public String getDirtImageFilePath() {
        return dirtImageFilePath;
    }

    public String getGrassImageFilePath() {
        return grassImageFilePath;
    }
}