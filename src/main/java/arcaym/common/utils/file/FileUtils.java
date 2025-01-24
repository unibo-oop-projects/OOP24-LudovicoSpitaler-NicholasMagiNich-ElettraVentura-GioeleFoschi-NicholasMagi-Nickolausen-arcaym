package arcaym.common.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        // temporal edit cuz I am tired
        final File deleteme = new File(Paths.get(APP_FOLDER, subFolder, name).toString());
        final boolean deleted = deleteme.delete();
        if (!deleted) {
            LOGGER.error("Error while deleating the file");
        }
    }

}
