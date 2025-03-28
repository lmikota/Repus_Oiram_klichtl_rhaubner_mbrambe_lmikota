package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import java.util.List;

public class StoryDialogs {

    private int levelID;
    private List<Dialog> dialogs;

    public StoryDialogs(int levelID, List<Dialog> dialogs) {
        setLevelID(levelID);
        setDialogs(dialogs);
    }

    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(List<Dialog> dialogs) {
        this.dialogs = dialogs;
    }
}