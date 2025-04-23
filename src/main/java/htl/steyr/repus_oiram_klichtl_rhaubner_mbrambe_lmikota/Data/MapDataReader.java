package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MapDataReader {
    public final String fileName = "src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/MapData.json";
    private final HashMap<Integer, MapData> mapHm;
    HashMap<Integer, String> filePaths;
    HashMap<String, HashMap<Integer, Double[]>> enemies;

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

            JsonObject enemiesObj = levelObj.getAsJsonObject("enemies");
            enemies = new HashMap<>();

            for (String enemyType : enemiesObj.keySet()) {
                JsonObject enemyGroup = enemiesObj.getAsJsonObject(enemyType);
                HashMap<Integer, Double[]> innerMap = new HashMap<>();

                for (String key : enemyGroup.keySet()) {
                    int intKey = Integer.parseInt(key);
                    JsonArray array = enemyGroup.getAsJsonArray(key);
                    Double[] values = new Double[array.size()];
                    for (int i = 0; i < array.size(); i++) {
                        values[i] = array.get(i).getAsDouble();
                    }
                    innerMap.put(intKey, values);
                }
                enemies.put(enemyType, innerMap);
            }

            MapData mapDataObject = new MapData(id, mapData, filePaths, enemies);
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
}