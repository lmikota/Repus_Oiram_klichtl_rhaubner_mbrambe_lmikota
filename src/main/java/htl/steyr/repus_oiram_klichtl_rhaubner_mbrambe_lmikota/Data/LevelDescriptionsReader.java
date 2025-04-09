package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;

import java.io.*;

public class LevelDescriptionsReader {

    public Gson gson = new Gson();
    public int selectedLevelID;

    public LevelDescriptionsReader(int selectedLevelID) {
        setSelectedLevelID(selectedLevelID);
    }

    public LevelDescription readLevelDescription() throws IOException {
        File file = new File("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/LevelDescriptions.json");

        Reader reader = new FileReader(file);
        LevelDescription[] levels = gson.fromJson(reader, LevelDescription[].class);

        for (LevelDescription level : levels) {
            if (level.getId() == selectedLevelID) {
                return level;
            }
        }
        return null;
    }

    public void setSelectedLevelID(int selectedLevelID) {
        this.selectedLevelID = selectedLevelID;
    }
}