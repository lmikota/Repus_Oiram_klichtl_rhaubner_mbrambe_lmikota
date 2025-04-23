package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import java.util.HashMap;

public class MapData {
    private int id;
    private int[][] mapData;
    private HashMap<Integer, String> filePaths;
    private HashMap<String, HashMap<Integer,Double[]>> enemies;

    public MapData(int id, int[][] mapData, HashMap<Integer, String> filePaths, HashMap<String, HashMap<Integer, Double[]>> enemies) {
        setMapData(mapData);
        setId(id);
        setFilePaths(filePaths);
        setEnemies(enemies);
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------- */

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

    public HashMap<String, HashMap<Integer, Double[]>> getEnemies() {
        return enemies;
    }

    public void setEnemies(HashMap<String, HashMap<Integer, Double[]>> enemies) {
        this.enemies = enemies;
    }
}