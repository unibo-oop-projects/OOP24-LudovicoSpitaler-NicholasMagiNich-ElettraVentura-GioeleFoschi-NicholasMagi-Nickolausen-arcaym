package arcaym.model.editor.saves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * An implementation of the {@link MapSerializer} Interface used to save data in a specific folder.
 * @param <T> the type of the key.
 * @param <U> the type of the value.
 */
public class MapSerializerImpl<T, U> implements MapSerializer<T, U> {

    private static final String EXTENSION = "bin";
    private static final Path SAVES_PATH = Paths.get(System.getProperty("user.home"), ".arcaym", "saves");
    private static final Logger LOGGER = LoggerFactory.getLogger(MapSerializerImpl.class);

    /**
     * {@inheritDoc}
     */
    // suppressed as the return value of mkdirs and createNewFile returns are usless:
    // if returns true the path has been created, if returns false the file/directory was already there
    // either way if an error occurs the executon of save is stopped
    @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED") 
    @Override
    public boolean serializeMap(final Map<T, U> map, final String uuid) {
        final String fileName = Paths.get(SAVES_PATH.toString(), uuid + "." + EXTENSION).toString();
        final File saveFile = new File(fileName);
        try (
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ) {
            saveFile.getParentFile().mkdirs();
            saveFile.createNewFile(); // if file already exists will do nothing
            oos.writeObject(map);
            oos.close();
        } catch (IOException | SecurityException e) {
            LOGGER.error("Error writing to file", e);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<T, U> getMapFromBinaryFile(final String uuid) {
        final String fileName = Paths.get(SAVES_PATH.toString(), uuid + "." + EXTENSION).toString();
        try (
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin)) {
            // in this directory we are sure that the file saved is of this type
            @SuppressWarnings("unchecked")
            final Map<T, U> returnMap = (Map<T, U>) ois.readObject();
            return returnMap;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error loading saved map", e);
        }
        // if error, return an empty map.
        return Map.of();
    }

}
