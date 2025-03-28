package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

public class MapData {
    private int id;
    private int[][] mapData;

    public MapData(int id, int[][] mapData) {
        setMapData(mapData);
        setId(id);
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
}
