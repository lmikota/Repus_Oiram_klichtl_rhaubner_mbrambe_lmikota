package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class StoryDialogsReader {

    private final Gson gson = new Gson();
    private int selectedLevelID;

    public StoryDialogsReader() {}

    /* ------------------------------------------------ JSON Reader ------------------------------------------------- */

    public StoryDialogs readStoryDialogs() throws FileNotFoundException {
        File file = new File("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/StoryDialogs.json");

        if (file == null) {
            throw new FileNotFoundException("StoryDialogs.json nicht gefunden!");
        }

        Reader reader = new FileReader(file);
        StoryDialogs[] dialogs = gson.fromJson(reader, StoryDialogs[].class);

        for (StoryDialogs storyDialogs : dialogs) {
            if (storyDialogs.getLevelID() == selectedLevelID) {
                return storyDialogs;
            }
        }
        return null;
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------- */

    public void setSelectedLevelID(int selectedLevelID) {
        this.selectedLevelID = selectedLevelID;
    }
}