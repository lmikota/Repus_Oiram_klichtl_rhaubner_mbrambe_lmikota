package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import java.util.Arrays;
import java.util.HashMap;

public class LocalUser {

    private String level_1;
    private String level_2;
    private String level_3;
    private String level_4;
    private String level_5;
    private String level_6;
    private HashMap<Integer, String> highscores;

    public LocalUser(String level_1, String level_2, String level_3, String level_4, String level_5, String level_6, HashMap<Integer, String> highscores) {
        setLevel_1(level_1);
        setLevel_2(level_2);
        setLevel_3(level_3);
        setLevel_4(level_4);
        setLevel_5(level_5);
        setLevel_6(level_6);
        setHighscores(highscores);
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------- */

    public String getLevel_1() {
        return level_1;
    }

    public void setLevel_1(String level_1) {
        this.level_1 = level_1;
    }

    public String getLevel_2() {
        return level_2;
    }

    public void setLevel_2(String level_2) {
        this.level_2 = level_2;
    }

    public String getLevel_3() {
        return level_3;
    }

    public void setLevel_3(String level_3) {
        this.level_3 = level_3;
    }

    public String getLevel_4() {
        return level_4;
    }

    public void setLevel_4(String level_4) {
        this.level_4 = level_4;
    }

    public String getLevel_5() {
        return level_5;
    }

    public void setLevel_5(String level_5) {
        this.level_5 = level_5;
    }

    public String getLevel_6() {
        return level_6;
    }

    public void setLevel_6(String level_6) {
        this.level_6 = level_6;
    }

    public HashMap<Integer, String> getHighscores() {
        return highscores;
    }

    public void setHighscores(HashMap<Integer, String> highscores) {
        this.highscores = highscores;
    }

    /* ---------------------------------------------- Update Functions ---------------------------------------------- */

    public void updateHighscores(int level, String highscore) {
        if (getHighscores().get(level).equals("no")) {
            // erster Highscore Eintrag
            highscores.replace(level, highscore);
        } else {
            String[] currentTimeParts = getHighscores().get(level).split(":");
            String[] newTimeParts = highscore.split(":");

            int currentMinutes = Integer.parseInt(currentTimeParts[0]);
            int currentSeconds = Integer.parseInt(currentTimeParts[1]);
            int newMinutes = Integer.parseInt(newTimeParts[0]);
            int newSeconds = Integer.parseInt(newTimeParts[1]);

            if (newMinutes < currentMinutes || (newMinutes == currentMinutes && newSeconds < currentSeconds)) {
                highscores.replace(level, highscore);
            }
        }
    }

    public void updateLevelLock(int level) {
        switch (level) {
            case 1:
                setLevel_2("unlocked");
                System.out.println(getLevel_1());
                break;
            case 2:
                setLevel_3("unlocked");
                break;
            case 3:
                setLevel_4("unlocked");
                break;
            case 4:
                setLevel_5("unlocked");
                break;
            case 5:
                setLevel_6("unlocked");
                break;
        }
    }
}