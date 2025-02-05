package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.common.utils.file.FileUtils;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.editor.saves.MetadataManager;
import arcaym.controller.editor.saves.MetadataManagerImpl;

/**
 * Class used to test the save feature of the levels metadata.
 */
final class TestMetadataManager {

    private static final int TEST_MULTIPLESAVES = 5;
    private static final int DEFAULT_GRID_WIDTH = 16;
    private static final int DEFAULT_GRID_HEIGHT = 9;
    private static final String SAVE_NAME = "testSave";
    private static final String SAVE_UUID = "testUUID";
    private static final EditorType SAVE_TYPE = EditorType.SANDBOX;
    private static final Position SAVE_DIMENSION = Position.of(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);

    private List<LevelMetadata> savedFiles;
    private MetadataManager manager;

    @BeforeEach
    void setup() {
        this.manager = new MetadataManagerImpl();
        this.savedFiles = new ArrayList<>();
    }

    private LevelMetadata createLevelMetadata(final int index) {
        return new LevelMetadata(SAVE_NAME + index, SAVE_UUID + index, SAVE_TYPE, SAVE_DIMENSION);
    }

    @AfterEach
    void clearFolderOfTests() {
        for (final LevelMetadata levelMetadata : savedFiles) {
            FileUtils.deleteFile(levelMetadata.uuid() + ".json", FileUtils.METADATA_FOLDER);
        }
    }

    @Test
    void testSave() {
        final LevelMetadata metadata = createLevelMetadata(0);
        if (manager.saveMetadata(metadata)) {
            assertTrue(manager.loadData().contains(metadata));
            this.savedFiles.add(metadata);
        } 
    }

    @Test
    void testMultipleSave() {
        boolean errorOccurred = false;
        for (int i = 0; i < TEST_MULTIPLESAVES && !errorOccurred; i++) {
            // list needed for clearing tests
            savedFiles.add(createLevelMetadata(i));
            // test save
            if (!manager.saveMetadata(savedFiles.get(i))) {
                errorOccurred = true;
            }
        }
        if (!errorOccurred) {
            final var listRead = manager.loadData();
            // check that each saved file is contained in the list loaded.
            for (final LevelMetadata levelMetadata : savedFiles) {
                assertTrue(listRead.contains(levelMetadata));
            }
        }
    }
}
