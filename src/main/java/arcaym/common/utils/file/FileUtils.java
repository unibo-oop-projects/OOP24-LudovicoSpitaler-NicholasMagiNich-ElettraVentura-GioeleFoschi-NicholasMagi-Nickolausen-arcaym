package arcaym.common.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Class used to create the application folders in the user home. 
 */
public final class FileUtils {

    private static final String APP_FOLDER = Paths.get(System.getProperty("user.home"), ".arcaym").toString();
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
    /**
     * The path to the grid saves.
     */
    public static final String SAVES_FOLDER = Paths.get(APP_FOLDER, "saves").toString();
    /**
     * The path to the levels metadata files.
     */
    public static final String METADATA_FOLDER = Paths.get(APP_FOLDER, "levelsMetadata").toString();

    private FileUtils() {
    }

    /**
     * Creates the directory .arcaym/saves in the user home.
     * @throws IOException if the directory cannot be created
     */
    public static void createSavesDirectory() {
        createDirectory(SAVES_FOLDER);
    }

    /**
     * Creates the directory .arcaym/levelsMetadata in the user home.
     * @throws IOException if the directory cannot be created
     */
    public static void createMetadataDirectory() {
        createDirectory(METADATA_FOLDER);
    }

    private static boolean createDirectory(final String path) {
        final File directory = new File(path);
        if (!directory.exists()) {
            final boolean folderCreated = directory.mkdirs();
            if (!folderCreated && !directory.exists()) {
                LOGGER.error("Error while creating directory");
                return false;
            }
        }
        return true;
    }

    /**
     * Deletes a file from a path.
     * @param name the path of the file
     * @param subFolder the folder to select (saves or levelMetadata)
     */
    public static void deleteFile(final String name, final String subFolder) {
        final var deleteMe = Paths.get(APP_FOLDER, subFolder, name);
        try {
            Files.deleteIfExists(deleteMe);
        } catch (IOException e) {
            LOGGER.error("Error while deleting the file '" + deleteMe + "'", e);
        }
    }

    /**
     * Deletes all the contents of the folder and the folder itself.
     * @param folder The path to the folder
     */
    public static void clearFolder(final File folder) {
        final File[] files = folder.listFiles();
        if (files != null) { // some JVMs return null for empty dirs
            for (final File f : files) {
                if (f.isDirectory()) {
                    clearFolder(f);
                } else {
                    if (!f.delete()) {
                        LOGGER.error("Error while deleating the file");
                    }
                }
            }
        }
        if (!folder.delete()) {
            LOGGER.error("Error while deleating folder");
        }
    }

    /**
     * Reads the content of a file from a specific path.
     * 
     * @param path The path of the file to read
     * @return Returns the string read or an {@link Optional#empty()} if there was an error while reading
     */
    public static Optional<String> readFromPath(final Path path) {
        try {
            return Optional.of(Files.readString(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * Converts a string to a specific object.
     * 
     * @param <T> The object type to convert the string to
     * @param classType The {@link Class} of the object to convert
     * @param content The string to convert
     * @return Returns an instance of the generic type given
     */
    public static <T> Optional<T> convertToObj(final Class<T> classType, final String content) {
        final Gson json = new Gson();
        try {
            return Optional.of(json.fromJson(content, classType));
        } catch (JsonSyntaxException e) {
            return Optional.empty();
        }
    }
}
