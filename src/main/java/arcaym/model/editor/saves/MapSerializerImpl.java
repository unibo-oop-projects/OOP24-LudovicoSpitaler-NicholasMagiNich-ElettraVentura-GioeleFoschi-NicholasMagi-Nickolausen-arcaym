package arcaym.model.editor.saves;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.utils.file.FileUtils;

/**
 * An implementation of the {@link MapSerializer} Interface used to save data in a specific folder.
 * @param <T> the type of the key.
 * @param <U> the type of the value.
 */
public class MapSerializerImpl<T, U> implements MapSerializer<T, U> {

    private static final String EXTENSION = ".bin";
    private static final Logger LOGGER = LoggerFactory.getLogger(MapSerializerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean serializeMap(final Map<T, U> map, final String uuid) {
        final String fileName = Paths.get(FileUtils.SAVES_FOLDER, uuid.concat(EXTENSION)).toString();
        FileUtils.createSavesDirectory();
        try (
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ) {
            oos.writeObject(map);
            oos.close();
        } catch (IOException e) {
            LOGGER.error("Error writing to file", e);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<T, U> loadMapFromBinaryFile(final String uuid) {
        final String fileName = Paths.get(FileUtils.SAVES_FOLDER, uuid.concat(EXTENSION)).toString();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteMap(final String uuid) {
        return FileUtils.deleteFile(uuid.concat(EXTENSION), FileUtils.SAVES_FOLDER);
    }

}
