package me.bartosz1.pastebin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;

public class DataService {

    private static final Logger LOGGER = Logger.getLogger(DataService.class.getName());
    public void initializeDataFolder() {
        LOGGER.fine("Initializing pastes directory");
        boolean b = new File("pastes").mkdir();
        LOGGER.fine("Directory init: "+b);
    }

    public String createPaste(String content) throws IOException {
        String id = Utils.generateID();
        File file = new File("pastes/" + id);
        if (file.createNewFile()) {
            Files.writeString(file.toPath(), content);
            LOGGER.fine("Wrote content to file "+id+" successfully");
            return id;
        } else {
            LOGGER.warning("Creating file "+id+" in pastes directory failed!");
            return null;
        }
    }

    public String getPaste(String id) throws IOException {
        File file = new File("pastes/" + id);
        if (file.exists()) {
            LOGGER.fine("File "+id+" exists, reading");
            StringBuilder contentBuilder = new StringBuilder();
            List<String> lines = Files.readAllLines(file.toPath());
            //yeah, I'm using fori instead of foreach just to avoid having blank newlines at the end
            for (int i = 0; i < lines.size(); i++) {
                contentBuilder.append(lines.get(i));
                if (i != lines.size() - 1) contentBuilder.append("\n");
            }
            LOGGER.fine("Finished reading "+id);
            return contentBuilder.toString();
        }
        LOGGER.fine("File "+id+" not found");
        return null;
    }
}
