package hw13.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static hw13.util.Texts.*;

public class LoggerUtil {

    private static final String LOG_FILE = "log.txt";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(String.format("%s : %s%n", LocalDateTime.now().format(FORMATTER), message) );
        } catch (IOException e) {
            System.err.printf(ERROR_WRITING_LOG, e.getMessage());
        }
    }
}
