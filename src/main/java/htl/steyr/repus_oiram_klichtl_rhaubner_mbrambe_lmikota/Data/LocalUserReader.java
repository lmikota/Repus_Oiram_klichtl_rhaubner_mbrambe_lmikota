package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LocalUserReader {

    private final Gson gson = new Gson();

    public LocalUser readLocalUsersUnlockedLevels() throws IOException {
        File file = new File("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON-Files/LocalUser.json");

        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            return gson.fromJson(reader, LocalUser.class);
        }
    }
}
