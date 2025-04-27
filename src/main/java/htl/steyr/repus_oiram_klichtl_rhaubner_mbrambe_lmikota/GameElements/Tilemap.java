package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.GameElements;

import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Application.GameplayApplication;
import htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data.MapDataReader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
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

    public static HashMap<Point, ImageView> items = new HashMap<>();

    public Tilemap(int[][] tileMapPattern) {
        setTileMapPattern(tileMapPattern);
        setCols(tileMapPattern[0].length);
        drawTileMap();
    }

    /**
     * This method draws the tile map on the screen.
     * It creates a new Pane to hold the tiles, initializes a MapDataReader
     * to read the mapdata, and then loops through each row and column
     * to create and position ImageViews for each tile in the map.
     * Depending on the tile type, it sets the image for each tile.
     * It also handles cases where the tile type is negative (representing items),
     * storing the corresponding ImageViews in a map for later use.
     */
    public void drawTileMap() {
        tyleMapPane = new Pane();

        try {
            reader = new MapDataReader();
        } catch (IOException e) {
            e.printStackTrace();
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
                    case -3:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(-3)))));
                        break;
                    case -2:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(-2)))));
                        break;
                    case -1:
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(reader.getMapHm().get(GameplayApplication.getSelectedLevel()).getFilePaths().get(-1)))));
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
                if (tileType < 0) {
                    Point pointKey = new Point(row, col);
                    System.out.println("Item bei" + pointKey);
                    items.put(pointKey, imageView);
                }
                getTyleMapPane().getChildren().add(imageView);
            }
        }
    }

    /**
     * Returns the Tilemaps length
     *
     * @return the tilemaps Length as int in pictures
     */

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
}