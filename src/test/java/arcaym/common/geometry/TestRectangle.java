package arcaym.common.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TestRectangle {

    private void assertRectangleEquals(
        final Point northWest,
        final Point southEast,
        final Point northEast,
        final Point southWest,
        final double height,
        final double base,
        final Rectangle rect
    ) {
        assertEquals(northWest, rect.northWest());
        assertEquals(southEast, rect.southEast());
        assertEquals(northEast, rect.northEast());
        assertEquals(southWest, rect.southWest());
        assertEquals(height, rect.height());
        assertEquals(base, rect.base());
    }

    @Test
    void testCreation() {
        final var northWest = Point.of(1, 2);
        final var southEast = Point.of(3, 6);
        final var northEast = Point.of(3, 2);
        final var southWest = Point.of(1, 6);
        final var height = 4;
        final var base = 2;
        final var rect = Rectangle.of(northWest, southEast);
        assertRectangleEquals(
            northWest,
            southEast,
            northEast,
            southWest,
            height,
            base,
            rect
        );
    }

    @Test
    void testCenteredSquare() {
        final var side = 3;
        final var center = Point.of(4, 6);
        final var northWest = Point.of(2.5, 4.5);
        final var northEast = Point.of(5.5, 4.5);
        final var southWest = Point.of(2.5, 7.5);
        final var southEast = Point.of(5.5, 7.5);
        final var height = 3;
        final var base = 3;
        final var square = Rectangle.centeredSquare(side, center);
        assertRectangleEquals(
            northWest,
            southEast,
            northEast,
            southWest,
            height,
            base,
            square
        );
    }

    @Test
    void testIsOutside() {
        final var northWest = Point.of(1, 2);
        final var southEast = Point.of(3, 6);
        final var rect = Rectangle.of(northWest, southEast);

        // for the sake of clarity on wich point is tested every time,
        // each one is kept in it's own variable
        final var outsideNorthWest = Point.of(2, 2);
        final var outsideSouthEast = Point.of(4, 7);
        assertFalse(rect.contains(Rectangle.of(outsideNorthWest, outsideSouthEast)));
        final var insideNorthWest = Point.of(2, 2);
        final var insideSouthEast = Point.of(3, 3);
        assertTrue(rect.contains(Rectangle.of(insideNorthWest, insideSouthEast)));
        assertTrue(rect.contains(rect));
    }

}
