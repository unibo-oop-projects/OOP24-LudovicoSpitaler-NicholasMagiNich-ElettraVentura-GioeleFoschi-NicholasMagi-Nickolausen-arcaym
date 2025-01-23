package arcaym.common.utils.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to create the application folders in the user home. 
 */
public final class DirectoryManager {

    private static final String APP_FOLDER = Paths.get(System.getProperty("user.home"), ".arcaym").toString();
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryManager.class);
    /**
     * The path to the grid saves.
     */
    public static final String SAVES_FOLDER = Paths.get(APP_FOLDER, "saves").toString();
    /**
     * The path to the levels metadata files.
     */
    public static final String METADATA_FOLDER = Paths.get(APP_FOLDER, "levelsMetadata").toString();

    private DirectoryManager() {
    }

    /**
     * Creates the directory .arcaym/saves in the user home.
     * @throws IOException if the directory cannot be created
     */
    public static void createSavesDirectory() throws IOException {
        createDirectory(SAVES_FOLDER);
    }

    /**
     * Creates the directory .arcaym/levelsMetadata in the user home.
     * @throws IOException if the directory cannot be created
     */
    public static void createMetadataDirectory() throws IOException {
        createDirectory(METADATA_FOLDER);
    }

    private static void createDirectory(final String path) throws IOException {
        final File directory = new File(path);
        if (!directory.exists()) {
            final boolean folderCreated = directory.mkdirs();
            if (!folderCreated && !directory.exists()) {
                LOGGER.error("Error while creating directory");
                throw new IOException("Error while creating directory: " + directory);
            }
        }
    } 
}
