package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.common.utils.file.FileUtils;
import arcaym.model.editor.saves.LevelMetadata;
import arcaym.model.editor.saves.MetadataManager;
import arcaym.model.editor.saves.MetadataManagerImpl;

/**
 * Class used to test the save feature of the levels metadata.
 */
final class TestMetadataManager {

    private static final int DEFAULT_GRID_WIDTH = 16;
    private static final int DEFAULT_GRID_HEIGHT = 9;
    private static final String SAVE_NAME = "testSave";
    private static final String SAVE_UUID = "qwertyuiop";
    private static final EditorType SAVE_TYPE = EditorType.SANDBOX;
    private static final Position SAVE_DIMENTION = Position.of(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);

    private MetadataManager manager;

    @BeforeEach
    void setup() {
        this.manager = new MetadataManagerImpl();
        FileUtils.clearFolder(new File(FileUtils.METADATA_FOLDER));
    }

    private LevelMetadata createLevelMetadata(final int index) {
        return new LevelMetadata(SAVE_NAME + index, SAVE_UUID + index, SAVE_TYPE, SAVE_DIMENTION);
    }

    @Test
    void testSave() {
        final LevelMetadata metadata = createLevelMetadata(0);
        if (manager.saveMetadata(metadata)) {
            assertEquals(metadata, manager.loadData().get(0));
        } 
    }

    @Test
    void testMultipleSave() {
        boolean failed = false;
        int filesSaved = 0;
        while (filesSaved < 10 && !failed) {
            if (manager.saveMetadata(createLevelMetadata(filesSaved))) {
                filesSaved++;
            } else {
                failed = true;
            }
        }
        assertEquals(filesSaved, manager.loadData().size());
    }

}
