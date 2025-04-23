package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

public class LevelDescription {

    private int id;
    private String name;
    private String description;
    private String difficulty;

    public LevelDescription(int id, String name, String description, String difficulty) {
        setId(id);
        setName(name);
        setDescription(description);
        setDifficulty(difficulty);
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}