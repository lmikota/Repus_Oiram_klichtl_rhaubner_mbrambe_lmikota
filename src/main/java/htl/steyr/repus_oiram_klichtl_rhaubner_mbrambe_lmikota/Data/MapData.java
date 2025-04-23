package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import java.util.HashMap;

public class MapData {
    private int id;
    private int[][] mapData;
    private HashMap<Integer, String> filePaths;

    public MapData(int id, int[][] mapData, HashMap<Integer, String> filePaths) {
        setMapData(mapData);
        setId(id);
        setFilePaths(filePaths);
    }

    public int getMapWidth(int tileSize) {
        int mapsize = 0;
        for (int i = 0; i < getMapData().length; i++) {
            mapsize = i * tileSize;
        }
        return mapsize;
    }

    public int[][] getMapData() {
        return mapData;
    }

    public void setMapData(int[][] mapData) {
        this.mapData = mapData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(HashMap<Integer, String> filePaths) {
        this.filePaths = filePaths;
    }
}
