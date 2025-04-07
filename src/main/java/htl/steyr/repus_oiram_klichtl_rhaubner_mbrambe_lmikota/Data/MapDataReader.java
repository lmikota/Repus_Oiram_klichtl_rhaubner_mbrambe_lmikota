package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
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
    /// @ToDo Über LevelID soll man bestimmte Level Laden können
    /// Man sollte sich mit ID aussuchen können, welches Level, jetzt passt das noch NICHT mit dem Ansatz
    /// Man wird verm. a for Schleife benötigen
    final String fileName = "src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/MapData.json";
    private HashMap<Integer,MapData> mapHm;

    public MapDataReader() throws IOException {
        initializeMapData(getFileName());
    }

    private void initializeMapData(String fileName) throws IOException {
        JsonReader reader;
        Gson gson = new Gson();
        reader = new JsonReader(new FileReader(fileName));
        Type type = new TypeToken<HashMap<Integer,MapData>>() {}.getType();
        setMapHm(gson.fromJson(reader,type));
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
}
