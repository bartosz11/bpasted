package me.bartosz1.pastebin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    //look at that compatibility
    private static final File TRUSTED_PATH = new File(Paths.get("").toAbsolutePath() + File.separator + "pastes");

    public static String generateID() {
        LOGGER.finest("Generating ID");
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    //very important
    public static boolean checkPathTraversal(String path) throws IOException {
        File file = new File(path);
        String trustedPathCanonicalPath = TRUSTED_PATH.getCanonicalPath();
        String requestedCanonicalPath = file.getCanonicalPath();
        return !requestedCanonicalPath.startsWith(trustedPathCanonicalPath);
    }
}
