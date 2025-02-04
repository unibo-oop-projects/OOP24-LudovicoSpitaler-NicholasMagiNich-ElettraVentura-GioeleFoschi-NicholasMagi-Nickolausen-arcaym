package arcaym.model.editor.saves;

import java.util.Map;

/**
 * An interface used to serialize a map.
 * @param <T> the type of the key.
 * @param <U> the type of the value.
 */
public interface MapSerializer<T, U> {
    /**
     * Saves into a file the given map.
     * @param map The map to serialize
     * @param uuid The name of the file that will containt the information
     * @return Returns true if the map was succesfully saved
     */
    boolean serializeMap(Map<T, U> map, String uuid);

    /**
     * Builds from a file a map.
     * @param uuid The name of the file to read
     * @return The created map
     */
    Map<T, U> loadMapFromBinaryFile(String uuid);

    /**
     * Delete a map file.
     * @param uuid the map to delete
     * @return if the operation concluded successfully
     */
    public boolean deleteMap(String uuid);
}
