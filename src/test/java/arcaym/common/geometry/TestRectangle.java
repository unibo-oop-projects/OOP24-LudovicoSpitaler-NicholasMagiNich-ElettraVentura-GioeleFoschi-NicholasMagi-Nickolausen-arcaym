package arcaym.common.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;

class TestRectangle {
    // CHECKSTYLE: MagicNumber OFF
    @Test
    void testCreation() {
        final var rect = Rectangle.of(Point.of(1, 2), Point.of(3, 6));
        assertEquals(Point.of(1, 2), rect.northEast());
        assertEquals(Point.of(3, 2), rect.northWest());
        assertEquals(Point.of(1, 6), rect.southEast());
        assertEquals(Point.of(3, 6), rect.southWest());
        assertEquals(4, rect.height());
        assertEquals(2, rect.base());
    }

    @Test
    @SuppressWarnings("checkstyle:magicnumber")
    void testCenteredSquare() {
        final var square = Rectangle.centeredSquare(3, Point.of(4, 6));
        assertEquals(Point.of(2.5, 4.5), square.northEast());
        assertEquals(Point.of(5.5, 4.5), square.northWest());
        assertEquals(Point.of(2.5, 7.5), square.southEast());
        assertEquals(Point.of(5.5, 7.5), square.southWest());
        assertEquals(3, square.height());
        assertEquals(3, square.base());
    }
    // CHECKSTYLE: MagicNumber ON

}
