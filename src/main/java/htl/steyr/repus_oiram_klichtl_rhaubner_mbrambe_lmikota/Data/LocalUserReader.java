package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LocalUserReader {

    private static final Gson gson = new Gson();

    public static LocalUser readLocalUser() throws IOException {
        File file = new File("src/main/resources/htl/steyr/repus_oiram_klichtl_rhaubner_mbrambe_lmikota/JSON/LocalUser.json");

        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            return gson.fromJson(reader, LocalUser.class);
        }
    }
}
