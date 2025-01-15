package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the {@link arcaym.model.editor.api.Grid} class.
 */
final class TestGrid {

    // private Grid basicGrid;

    // @BeforeEach
    // public void init() {
    //     this.basicGrid = new GridImpl(5, 5);
    // }

    @Test
    void testAddObjects() {
        // this.basicGrid.setObjects(List.of(
        //     new BasePoint(0, 0),
        //     new BasePoint(0, 1),
        //     new BasePoint(1, 0),
        //     new BasePoint(1, 1)
        // ), GameObjectType.FLOOR);

        // this.basicGrid.setObjects(List.of(
        //     new BasePoint(0, 0),
        //     new BasePoint(1, 3),
        //     new BasePoint(4, 4),
        //     new BasePoint(1, 4)
        // ), GameObjectType.SPIKE);

        // this.basicGrid.setObjects(List.of(
        //     new BasePoint(0, 0),
        //     new BasePoint(1, 2),
        //     new BasePoint(4, 0),
        //     new BasePoint(1, 1)
        // ), GameObjectType.COIN);

        // assertEquals(Set.of(
        //     GameObjectType.COIN,
        //     GameObjectType.FLOOR,
        //     GameObjectType.SPIKE),
        // basicGrid.getObjects(Point.zero()));
        assertEquals(0, 0);
    }

    @Test
    void testRemoveObjects() {
        assertEquals(0, 0);
    }

    @Test
    void testFailConstraints() {
        assertEquals(0, 0);
    }

    @Test
    void testRestore() {
        assertEquals(0, 0);
    }

}
