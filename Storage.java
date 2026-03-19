import com.google.gson.Gson;
import java.io.*;
import java.util.*;

public class Storage {
    private Set<String> seen;
    private String file;
    private Gson gson = new Gson();

    public Storage(String file) {
        this.file = file;
        load();
    }

    private void load() {
        try {
            Reader reader = new FileReader(file);
            seen = gson.fromJson(reader, Set.class);
            if (seen == null) seen = new HashSet<>();
        } catch (Exception e) {
            seen = new HashSet<>();
        }
    }

    public boolean hasSeen(String link) { return seen.contains(link); }
    public void markSeen(String link) { seen.add(link); }

    public void save() throws Exception {
        Writer writer = new FileWriter(file);
        gson.toJson(seen, writer);
        writer.close();
    }
}
