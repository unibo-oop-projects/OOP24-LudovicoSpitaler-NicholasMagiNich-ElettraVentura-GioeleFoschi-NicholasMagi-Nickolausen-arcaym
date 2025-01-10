package arcaym.common.point;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.common.point.PointInstancesProvider.PointConstructor;

class TestPoint {

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testGetters(final PointConstructor constructor) {
        final var x = 1;
        final var y = 2;
        final var point = constructor.apply(x, y);
        assertEquals(x, point.x());
        assertEquals(y, point.y());
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testEqualsByCoordinates(final PointConstructor constructor) {
        final var x = 1;
        final var y = 2;
        assertEquals(constructor.apply(x, y), constructor.apply(x, y));
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testInvertX(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(-point.x(), point.y()), point.invertX());
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testInvertY(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(point.x(), -point.y()), point.invertY());
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testInvert(final PointConstructor constructor) {
        final var point = constructor.apply(1, 2);
        assertEquals(constructor.apply(-point.x(), -point.y()), point.invert());
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testSum(final PointConstructor constructor) {
        final var p1 = constructor.apply(1, 2);
        final var p2 = constructor.apply(2, -1);
        assertEquals(constructor.apply(3, 1), p1.sum(p2));
    }

    @ParameterizedTest
    @ArgumentsSource(PointInstancesProvider.class)
    void testSubtract(final PointConstructor constructor) {
        final var p1 = constructor.apply(1, 2);
        final var p2 = constructor.apply(2, -1);
        assertEquals(constructor.apply(-1, 3), p1.subtract(p2));
    }

}
