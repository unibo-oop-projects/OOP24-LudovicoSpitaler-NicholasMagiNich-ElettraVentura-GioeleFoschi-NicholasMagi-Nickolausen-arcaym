package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.model.editor.api.GridModel;
import arcaym.model.editor.impl.GridModelImpl;
import arcaym.model.game.objects.GameObjectType;

final class TestGridUndo {

    private static final int SIZE = 20;

    private GridModel grid;

    @BeforeEach
    void setup() {
        this.grid = new GridModelImpl(EditorType.SANDBOX, SIZE, SIZE);
    }

    @Test
    void testCanUndo() {
        // test if cannot undo
        assertFalse(this.grid.canUndo());
        // add objects
        assertDoesNotThrow(() -> this.grid.placeObjects(Set.of(Position.of(0, 0)), GameObjectType.COIN));
        // test if can undo
        assertTrue(this.grid.canUndo());
        // recover state
        this.grid.undo();
        // test if cannot undo
        assertFalse(this.grid.canUndo());
    }

    @Test
    void testRecoverAfterPlacement() {
        // add some objects
        assertDoesNotThrow(() -> 
            this.grid.placeObjects(
                Set.of(Position.of(0, 0),
                Position.of(0, 1)), GameObjectType.COIN));

        assertDoesNotThrow(() -> 
            this.grid.placeObjects(
                Set.of(Position.of(0, 0),
                Position.of(0, 1)), GameObjectType.WALL));
        // undo last addiction
        grid.undo();
        // should have recovered the state
        assertEquals(
            Map.of(
                Position.of(0, 0), List.of(GameObjectType.FLOOR, GameObjectType.COIN),
                Position.of(0, 1), List.of(GameObjectType.FLOOR, GameObjectType.COIN)), grid.getUpdatedGrid());
        // restore original grid state
        grid.undo();
        // check that the grid 
        assertEquals(
            Map.of(
                    Position.of(0, 0), List.of(GameObjectType.FLOOR),
                    Position.of(0, 1), List.of(GameObjectType.FLOOR)),
                grid.getUpdatedGrid());
    }

    @Test
    void testRecoverAfterDelete() {
        // add some objects
        assertDoesNotThrow(() -> this.grid.placeObjects(
                Set.of(Position.of(0, 0),
                        Position.of(0, 1)),
                GameObjectType.COIN));

        assertDoesNotThrow(() -> this.grid.placeObjects(
                Set.of(Position.of(0, 0),
                        Position.of(0, 1)),
                GameObjectType.WALL));
        // clear map in those position
        assertDoesNotThrow(() -> this.grid.removeObjects(
            Set.of(
                Position.of(0, 0),
                Position.of(0, 1))));
        assertEquals(Map.of(
            Position.of(0, 0), List.of(GameObjectType.FLOOR),
            Position.of(0, 1), List.of(GameObjectType.FLOOR)), this.grid.getUpdatedGrid());
        // undo last operation
        assertTrue(this.grid.canUndo());
        this.grid.undo();
        // check if the grid is in the correct state
        assertEquals(Map.of(
                Position.of(0, 0), List.of(GameObjectType.WALL, GameObjectType.COIN),
                Position.of(0, 1), List.of(GameObjectType.WALL, GameObjectType.COIN)), this.grid.getUpdatedGrid());
    }
}
