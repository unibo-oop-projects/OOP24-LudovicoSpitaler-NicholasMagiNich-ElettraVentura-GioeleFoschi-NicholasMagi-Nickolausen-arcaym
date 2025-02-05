package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import arcaym.common.utils.Position;
import arcaym.model.editor.api.MapConstraint;
import arcaym.model.editor.api.MapConstraintsFactory;
import arcaym.model.editor.impl.MapConstraintFactoryImpl;

/**
 * Tests the various {@link MapConstraint} created by the {@link MapConstraintsFactory}.
 */

final class TestConstraints {

    static final String TEST_ERROR = "test";
    static final int DEFAULT_MAX_WIDTH = 30;
    static final int DEFAULT_MAX_HEIGHT = 30;
    static final int MAX_OBJECTS = 11;
    static final int MIN_OBJECTS = 5;

    private final MapConstraintsFactory factory = new MapConstraintFactoryImpl();
    private final Random rd = new Random();

    private Set<Position> generateAdjacentPositionSet() {
        final var initialPos = Position.of(rd.nextInt(DEFAULT_MAX_WIDTH), rd.nextInt(DEFAULT_MAX_HEIGHT));
        return IntStream.range(0, DEFAULT_MAX_WIDTH)
            .mapToObj(i -> i)
            .flatMap(x -> IntStream.range(0, DEFAULT_MAX_HEIGHT)
            .mapToObj(y -> new Position(x, y)))
            .filter(p -> Math.abs(p.x() - initialPos.x()) <= 1 && Math.abs(p.y() - initialPos.y()) <= 1)
            .collect(Collectors.toSet());
    }

    @Test
    void testAdjacencyConstraint() {
        final Set<Position> testPos = generateAdjacentPositionSet();
        assertDoesNotThrow(() -> factory.adjacencyConstraint().checkConstraint(testPos));
    }

    @Test
    void testAdjacencyConstraintFail() {
        final Set<Position> testPos = generateAdjacentPositionSet();
        testPos.add(new Position(DEFAULT_MAX_WIDTH + 2, DEFAULT_MAX_HEIGHT + 2)); // add a cell that will never be nearby
        assertThrows(ConstraintFailedException.class, () -> factory.adjacencyConstraint().checkConstraint(testPos));
    }

    @Test
    void testSingleBlockConstraint() {
        final var posSet = Set.of(Position.of(rd.nextInt(DEFAULT_MAX_WIDTH), rd.nextInt(DEFAULT_MAX_HEIGHT)));
        assertDoesNotThrow(() -> factory.singleBlockConstraint(TEST_ERROR).checkConstraint(posSet));
    }

    @Test
    void testSingleBlockConstraintFail() {
        final var posSet = createSetOfMinSize(2);
        assertThrows(ConstraintFailedException.class, () -> factory.singleBlockConstraint(TEST_ERROR).checkConstraint(posSet));
    }

    @Test
    void testMaxBlocksConstraint() {
        final int maxNumOfObjects = rd.nextInt(MIN_OBJECTS, MAX_OBJECTS);
        assertDoesNotThrow(() -> factory.maxNumberOfBlocks(maxNumOfObjects, TEST_ERROR)
            .checkConstraint(createSetOfMinSize(maxNumOfObjects - 2))); // create a set with one less object than the max
        assertDoesNotThrow(() -> factory.maxNumberOfBlocks(maxNumOfObjects, TEST_ERROR)
                .checkConstraint(createSetOfMinSize(maxNumOfObjects - 1))); // edge test max objects == num objects
    }

    @Test
    void testMaxBlocksConstraintFail() {
        final int maxNumOfObjects = rd.nextInt(MIN_OBJECTS, MAX_OBJECTS);
        assertThrows(ConstraintFailedException.class, () -> factory.maxNumberOfBlocks(maxNumOfObjects, TEST_ERROR)
                .checkConstraint(createSetOfMinSize(maxNumOfObjects + MIN_OBJECTS)));
    }

    @Test
    void testMinBlocksConstraint() {
        final int minNumOfObjects = rd.nextInt(MIN_OBJECTS, MAX_OBJECTS);
        assertDoesNotThrow(() -> factory.minNumberOfBlocks(minNumOfObjects, TEST_ERROR)
            .checkConstraint(createSetOfMinSize(minNumOfObjects + MIN_OBJECTS)));
        assertDoesNotThrow(() -> factory.minNumberOfBlocks(minNumOfObjects, TEST_ERROR)
            .checkConstraint(createSetOfMinSize(minNumOfObjects  - 1))); // min objects == numObjects
    }

    @Test 
    void testMinBlocksConstraintFail() {
        final int minNumOfObjects = rd.nextInt(MIN_OBJECTS, MAX_OBJECTS);
        assertThrows(ConstraintFailedException.class, () -> factory.minNumberOfBlocks(minNumOfObjects, TEST_ERROR)
                .checkConstraint(createSetOfMinSize(minNumOfObjects - 2)));
    }

    private Set<Position> createSetOfMinSize(final int minSize) {
        final var posSet = new HashSet<Position>();
        while (posSet.size() <= minSize) {
            posSet.add(Position.of(rd.nextInt(DEFAULT_MAX_WIDTH), rd.nextInt(DEFAULT_MAX_HEIGHT)));
        }
        return posSet;
    }
}
