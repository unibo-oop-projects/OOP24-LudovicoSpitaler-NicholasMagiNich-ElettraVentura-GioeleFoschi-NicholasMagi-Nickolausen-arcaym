package arcaym.common.point;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.common.point.api.PointFactory;

class TestPointFactory {

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testOfCoordinates(final PointFactory factory) {
        final var x = 1;
        final var y = 2;
        final var point = factory.ofCoordinates(x, y);
        assertEquals(x, point.x());
        assertEquals(y, point.y());
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testEqualsByCoordinates(final PointFactory factory) {
        final var x = 1;
        final var y = 2;
        assertEquals(factory.ofCoordinates(x, y), factory.ofCoordinates(x, y));
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testInvertX(final PointFactory factory) {
        final var point = factory.ofCoordinates(1, 2);
        assertEquals(factory.ofCoordinates(-point.x(), point.y()), factory.invertX(point));
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testInvertY(final PointFactory factory) {
        final var point = factory.ofCoordinates(1, 2);
        assertEquals(factory.ofCoordinates(point.x(), -point.y()), factory.invertY(point));
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testInvert(final PointFactory factory) {
        final var point = factory.ofCoordinates(1, 2);
        assertEquals(factory.ofCoordinates(-point.x(), -point.y()), factory.invert(point));
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testSum(final PointFactory factory) {
        final var p1 = factory.ofCoordinates(1, 2);
        final var p2 = factory.ofCoordinates(2, -1);
        assertEquals(factory.ofCoordinates(3, 1), factory.sum(p1, p2));
    }

    @ParameterizedTest
    @ArgumentsSource(PointFactoriesProvider.class)
    void testSubtract(final PointFactory factory) {
        final var p1 = factory.ofCoordinates(1, 2);
        final var p2 = factory.ofCoordinates(2, -1);
        assertEquals(factory.ofCoordinates(-1, 3), factory.subtract(p1, p2));
    }

}
