package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapDataReader {
    final String fileName = "src/main/java/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/Data/MapData.json";
    private int mapData[][];
    public MapDataReader() throws IOException {
        setMapData(getFileName());
    }

    private void setMapData(String fileName) throws IOException {
        JsonReader reader;
        Gson gson = new Gson();
        reader = new JsonReader(new FileReader(fileName));
        this.mapData = gson.fromJson(reader, int[][].class);
        reader.close();
    }

    public int getMapWidth(int tileSize) {
        int mapsize = 0;
        for ( int i = 0; i < getMapData().length; i++) {
            mapsize = i * tileSize;
        }
        return mapsize;
    }

    public String getFileName() {
        return fileName;
    }

    public int[][] getMapData() {
        return mapData;
    }
}
