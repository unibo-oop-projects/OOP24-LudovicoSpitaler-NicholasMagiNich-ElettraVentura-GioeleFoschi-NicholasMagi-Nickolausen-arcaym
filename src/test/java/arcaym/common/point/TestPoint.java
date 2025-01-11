package arcaym.common.point;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.common.point.PointsProvider.PointConstructor;

class TestPoint {

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testEqualsByCoordinates(final PointConstructor constructor) {
        final var x = 1;
        final var y = 2;
        assertEquals(constructor.apply(x, y), constructor.apply(x, y));
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testInvertX(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(-point.x(), point.y()), point.invertX());
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testInvertY(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(point.x(), -point.y()), point.invertY());
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testInvert(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(-point.x(), -point.y()), point.invert());
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testSum(final PointConstructor constructor) {
        final var p1 = constructor.apply(1, 2);
        final var p2 = constructor.apply(2, -1);
        assertEquals(constructor.apply(3, 1), p1.sum(p2));
    }

    @ParameterizedTest
    @ArgumentsSource(PointsProvider.class)
    void testSubtract(final PointConstructor constructor) {
        final var p1 = constructor.apply(1, 2);
        final var p2 = constructor.apply(2, -1);
        assertEquals(constructor.apply(-1, 3), p1.subtract(p2));
    }

}
