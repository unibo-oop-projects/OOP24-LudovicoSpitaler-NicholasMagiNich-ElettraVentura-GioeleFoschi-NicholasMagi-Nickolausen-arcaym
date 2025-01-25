package arcaym.common.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;

class TestRectangle {

    @Test
    void testCreation() {
        final var northEast = Point.of(1, 2);
        final var southWest = Point.of(3, 6);
        final var northWest = Point.of(3, 2);
        final var southEast = Point.of(1, 6);
        final var height = 4;
        final var base = 2;

        final var rect = Rectangle.of(northEast, southWest);
        assertEquals(northEast, rect.northEast());
        assertEquals(northWest, rect.northWest());
        assertEquals(southEast, rect.southEast());
        assertEquals(southWest, rect.southWest());
        assertEquals(height, rect.height());
        assertEquals(base, rect.base());
    }

    @Test
    void testCenteredSquare() {
        final var side = 3;
        final var center = Point.of(4, 6);
        final var northEast = Point.of(2.5, 4.5);
        final var northWest = Point.of(5.5, 4.5);
        final var southEast = Point.of(2.5, 7.5);
        final var southWest = Point.of(5.5, 7.5);
        final var height = 3;
        final var base = 3;

        final var square = Rectangle.centeredSquare(side, center);
        assertEquals(northEast, square.northEast());
        assertEquals(northWest, square.northWest());
        assertEquals(southEast, square.southEast());
        assertEquals(southWest, square.southWest());
        assertEquals(height, square.height());
        assertEquals(base, square.base());
    }

}
