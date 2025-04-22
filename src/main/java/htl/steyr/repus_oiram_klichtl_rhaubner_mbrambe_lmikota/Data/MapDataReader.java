package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapDataReader {
    final String fileName = "src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/MapData.json";
    private HashMap<Integer,MapData> mapHm;
    HashMap<Integer, String> filePaths;

    public MapDataReader() throws IOException {
        mapHm = new HashMap<>();
        initializeMapData(getFileName());
    }

    private void initializeMapData(String fileName) throws IOException {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        for (String levelId : jsonObject.keySet()) {
            JsonObject levelObj = jsonObject.getAsJsonObject(levelId);
            int id = levelObj.get("levelID").getAsInt();

            JsonArray mapDataArray = levelObj.getAsJsonArray("mapData");
            int[][] mapData = new int[mapDataArray.size()][];
            for (int i = 0; i < mapDataArray.size(); i++) {
                JsonArray row = mapDataArray.get(i).getAsJsonArray();
                mapData[i] = new int[row.size()];
                for (int j = 0; j < row.size(); j++) {
                    mapData[i][j] = row.get(j).getAsInt();
                }
            }

            JsonObject filePathsObj = levelObj.getAsJsonObject("filePaths");
            filePaths = new HashMap<>();
            for (String key : filePathsObj.keySet()) {
                int numKey = Integer.parseInt(key);
                filePaths.put(numKey, filePathsObj.get(key).getAsString());
            }
            MapData mapDataObject = new MapData(id, mapData, filePaths);
            mapHm.put(id, mapDataObject);
        }

        reader.close();
    }

    public String getFileName() {
        return fileName;
    }


    public HashMap<Integer, MapData> getMapHm() {
        return mapHm;
    }

    public void setMapHm(HashMap<Integer, MapData> mapHm) {
        this.mapHm = mapHm;
    }

    public HashMap<Integer, String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(HashMap<Integer, String> filePaths) {
        this.filePaths = filePaths;
    }
}
