package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.common.utils.file.FileUtils;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.impl.GridImpl;
import arcaym.model.editor.saves.LevelMetadata;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Test class for the {@link arcaym.model.editor.api.Grid} class.
 */
final class TestGrid {

    private static final int DEFAULT_GRID_WIDTH = 16;
    private static final int DEFAULT_GRID_HEIGHT = 9;
    private static final String TEST_SAVE_NAME = "testSave";
    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR;
    private static final Random RD = new Random();

    private Grid basicGrid;

    @AfterAll
    public static void cleanUp() {
        FileUtils.deleteFile(TEST_SAVE_NAME.concat(".bin"), "saves");
    }

    private void setUpSandboxGrid() {
        this.basicGrid = new GridImpl(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT, EditorType.SANDBOX);
    }

    private void setUpNormalGrid() {
        this.basicGrid = new GridImpl(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT, EditorType.NORMAL);
    }

    @Test
    void testSandboxMapPlaceObject() {
        setUpSandboxGrid();
        final var pos = getRandomPosition();
        // check for the default block
        assertEquals(List.of(DEFAUL_TYPE), basicGrid.getObjects(pos));
        // add another type of block
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.WALL));
        // // check if the object has been added
        assertEquals(List.of(GameObjectType.WALL), basicGrid.getObjects(pos));
        // add other layers to the cell
        addLayeredObject(pos);
        assertEquals(List.of(
                GameObjectType.WALL,
                GameObjectType.SPIKE,
                GameObjectType.COIN),
                basicGrid.getObjects(pos));
        // test set outside boundary
        assertThrows(EditorGridException.class,
                () -> basicGrid.setObjects(Set.of(Position.of(-1, -1)), DEFAUL_TYPE));
    }

    @Test
    void testSandboxRemoveObjects() {
        setUpSandboxGrid();
        final var pos = getRandomPosition();
        // add objects to cell
        addLayeredObject(pos);
        // remove objects
        assertDoesNotThrow(() -> basicGrid.removeObjects(Set.of(pos)));
        assertEquals(List.of(DEFAUL_TYPE), basicGrid.getObjects(pos));
        // test set outside boundary
        assertThrows(EditorGridException.class,
                () -> basicGrid.removeObjects(Set.of(Position.of(-1, -1))));
    }

    @Test
    void testSaveAndLoadSandboxGrid() {
        setUpSandboxGrid();
        final var pos = getRandomPosition();
        // add some objects to the grid
        addLayeredObject(pos);
        // check if the objects are saved correctly
        assertEquals(List.of(
            GameObjectType.WALL,
            GameObjectType.SPIKE,
            GameObjectType.COIN),
            basicGrid.getObjects(pos));
        // save the grid
        if (this.basicGrid.saveState(TEST_SAVE_NAME)) {
            // create a new grid based on the metadata
            this.basicGrid = new GridImpl(
                new LevelMetadata(
                    "Test",
                    TEST_SAVE_NAME,
                    EditorType.SANDBOX,
                    Position.of(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT)));
                    // check the position is still correct
            assertEquals(List.of(
                GameObjectType.WALL,
                GameObjectType.SPIKE,
                GameObjectType.COIN),
                basicGrid.getObjects(pos));
        }
        // ho-lee-fook, it works
    }

    @Test
    void testSaveAndLoadNormalGrid() {
        setUpNormalGrid();
        // add some objects to the grid
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(
            Position.of(0, 0),
            Position.of(0, 1),
            Position.of(1, 0)), GameObjectType.WIN_GOAL));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(Position.of(1, 1)), GameObjectType.USER_PLAYER));
        // check if the constraints work properly
        assertThrows(EditorGridException.class, 
            () -> basicGrid.setObjects(Set.of(Position.of(2, 2)), GameObjectType.WIN_GOAL));
        assertThrows(EditorGridException.class,
            () -> basicGrid.setObjects(Set.of(Position.of(2, 2)), GameObjectType.USER_PLAYER));
        // save the grid
        if (this.basicGrid.saveState(TEST_SAVE_NAME)) {
            // create a new grid based on the metadata
            this.basicGrid = new GridImpl(
                new LevelMetadata(
                    "Test",
                    TEST_SAVE_NAME,
                    EditorType.NORMAL,
                    Position.of(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT)));
            // check the constraints are correctly added.
            assertThrows(EditorGridException.class,
                    () -> basicGrid.setObjects(Set.of(Position.of(2, 2)), GameObjectType.WIN_GOAL));
            assertThrows(EditorGridException.class,
                    () -> basicGrid.setObjects(Set.of(Position.of(2, 2)), GameObjectType.USER_PLAYER));
        }
        // ho-lee-fook, it works
    }

    private Position getRandomPosition() {
        return Position.of(RD.nextInt(DEFAULT_GRID_WIDTH), RD.nextInt(DEFAULT_GRID_HEIGHT));
    }

    private void addLayeredObject(final Position pos) {
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.WALL));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.SPIKE));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.COIN));
    }
}
