package arcaym.controller.editor.saves;

import java.util.List;

/**
 * An interface that manages all the saved metadata.
 * Its responsible for storing ang retriving {@link LevelMetadata} from the disk
 */
public interface MetadataManager {
    /**
     * Saves the data in a file for later use.
     * @param metadata The instance to save
     * @return Returns true if the level was saved correctly, false otherwise
     */
    boolean saveMetadata(LevelMetadata metadata);

    /**
     * Loads all the saved data.
     * @return A list of all the data loaded from files
     */
    List<LevelMetadata> loadData();
}
