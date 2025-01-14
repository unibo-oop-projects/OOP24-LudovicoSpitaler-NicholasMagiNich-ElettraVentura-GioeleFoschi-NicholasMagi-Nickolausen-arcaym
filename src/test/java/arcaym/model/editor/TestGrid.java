package arcaym.model.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.point.api.Point;
import arcaym.common.point.impl.BasePoint;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.impl.GridImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Test class for the {@link arcaym.model.editor.api.Grid} class.
 */
public final class TestGrid {

    private Grid basicGrid;

    @BeforeEach
    public void init() {
        this.basicGrid = new GridImpl(5, 5);
    }

    @Test
    public void testAddObjects(){
        this.basicGrid.setObjects(List.of(
            new BasePoint(0, 0),
            new BasePoint(0, 1),
            new BasePoint(1, 0),
            new BasePoint(1, 1)
        ), GameObjectType.FLOOR);

        this.basicGrid.setObjects(List.of(
            new BasePoint(0, 0),
            new BasePoint(1, 3),
            new BasePoint(4, 4),
            new BasePoint(1, 4)
        ), GameObjectType.SPIKE);

        this.basicGrid.setObjects(List.of(
            new BasePoint(0, 0),
            new BasePoint(1, 2),
            new BasePoint(4, 0),
            new BasePoint(1, 1)
        ), GameObjectType.COIN);

        assertEquals(Set.of(
            GameObjectType.COIN,
            GameObjectType.FLOOR,
            GameObjectType.SPIKE),
        basicGrid.getObjects(Point.zero()));
    }

    @Test
    public void testRemoveObjects(){
        assertEquals(0, 0);
    }

    @Test
    public void testFailConstraints(){
        assertEquals(0, 0);
    }

    @Test
    public void testRestore(){
        assertEquals(0, 0);
    }

}
