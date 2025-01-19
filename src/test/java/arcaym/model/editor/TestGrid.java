package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.impl.GridImpl;
import arcaym.model.editor.impl.MapConstraintFactoryImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Test class for the {@link arcaym.model.editor.api.Grid} class.
 */
final class TestGrid {

    private static final int DEFAULT_GRID_WIDTH = 16;
    private static final int DEFAULT_GRID_HEIGHT = 9;
    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR;
    private static final Random RD = new Random();

    private Grid basicGrid;

    @BeforeEach
    void setup() {
        basicGrid = new GridImpl(DEFAULT_GRID_WIDTH, DEFAULT_GRID_HEIGHT);
    }

    @Test
    void testMapWithNoConstraints() {
        final var pos = Position.of(RD.nextInt(DEFAULT_GRID_WIDTH), RD.nextInt(DEFAULT_GRID_HEIGHT));
        // check for the default block
        assertEquals(List.of(DEFAUL_TYPE), basicGrid.getObjects(pos));
        // add another type of block
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.WALL));
        // check if the object has been added
        assertEquals(List.of(GameObjectType.WALL), basicGrid.getObjects(pos));
        // add other layers to the cell
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.SPIKE));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.COIN));
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
    void testRemoveObjects() {
        final var pos = Position.of(RD.nextInt(DEFAULT_GRID_WIDTH), RD.nextInt(DEFAULT_GRID_HEIGHT));
        // add objects to cell
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.WALL));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.SPIKE));
        assertDoesNotThrow(() -> basicGrid.setObjects(Set.of(pos), GameObjectType.COIN));
        assertEquals(List.of(
                GameObjectType.WALL,
                GameObjectType.SPIKE,
                GameObjectType.COIN),
                basicGrid.getObjects(pos));
        // remove objects
        assertDoesNotThrow(() -> basicGrid.removeObjects(Set.of(pos)));
        assertEquals(List.of(DEFAUL_TYPE), basicGrid.getObjects(pos));
        // test set outside boundary
        assertThrows(EditorGridException.class,
                () -> basicGrid.removeObjects(Set.of(Position.of(-1, -1))));
    }

    @Test
    void testFailConstraints() {
        final var constraintFactory = new MapConstraintFactoryImpl();
        this.basicGrid.setObjectConstraint(constraintFactory.singleBlockConstraint(), GameObjectType.USER_PLAYER);
        // add single block constraint
        assertDoesNotThrow(() -> this.basicGrid.setObjects(Set.of(Position.of(0, 0)), GameObjectType.USER_PLAYER));
        assertThrows(EditorGridException.class,
            () -> this.basicGrid.setObjects(Set.of(Position.of(1, 1)),
                GameObjectType.USER_PLAYER));
        // add different constraint
        this.basicGrid.setObjectConstraint(constraintFactory.adjacencyConstraint(), GameObjectType.WIN_GOAL);
        assertDoesNotThrow(() -> this.basicGrid.setObjects(getadjacentCells(), GameObjectType.WIN_GOAL));
        // assertThrows(EditorGridException.class, this.basicGrid.setObjects(randomCell, GameObjectType.WIN_GOAL));
        // kinda hard to check this so for now I wont, the constraint was already tested in the TestConstraint class
        // check that the old constraint was not overwritten
        assertThrows(EditorGridException.class,
            () -> this.basicGrid.setObjects(Set.of(Position.of(1, 1)),
                GameObjectType.USER_PLAYER));
    }

    private Set<Position> getadjacentCells() {
        final var initialPos = Position.of(RD.nextInt(DEFAULT_GRID_WIDTH), RD.nextInt(DEFAULT_GRID_HEIGHT));
        return IntStream.range(0, DEFAULT_GRID_WIDTH)
            .mapToObj(i -> i)
            .flatMap(x -> IntStream.range(0, DEFAULT_GRID_HEIGHT)
            .mapToObj(y -> new Position(x, y)))
            .filter(p -> Math.abs(p.x() - initialPos.x()) <= 1 && Math.abs(p.y() - initialPos.y()) <= 1)
            .collect(Collectors.toSet());
    }
}
