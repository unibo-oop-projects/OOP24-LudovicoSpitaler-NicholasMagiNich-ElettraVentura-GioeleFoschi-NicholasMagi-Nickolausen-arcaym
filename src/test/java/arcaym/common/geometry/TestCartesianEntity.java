package arcaym.common.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.common.geometry.CartesianEntitiesProvider.CartesianEntityConstructor;
import arcaym.common.geometry.api.CartesianEntity;

class TestCartesianEntity<T extends CartesianEntity<T>> {

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testEqualsByCoordinates(final CartesianEntityConstructor<T> constructor) {
        final var x = 1.0;
        final var y = 2.0;
        assertEquals(constructor.apply(x, y), constructor.apply(x, y));
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testInvertX(final CartesianEntityConstructor<T> constructor) {
        final var entity = constructor.apply(1.0, 2.0);
        assertEquals(constructor.apply(-entity.x(), entity.y()), entity.invertX());
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testInvertY(final CartesianEntityConstructor<T> constructor) {
        final var entity = constructor.apply(1.0, 2.0);
        assertEquals(constructor.apply(entity.x(), -entity.y()), entity.invertY());
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testInvert(final CartesianEntityConstructor<T> constructor) {
        final var entity = constructor.apply(1.0, 2.0);
        assertEquals(constructor.apply(-entity.x(), -entity.y()), entity.invert());
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testSum(final CartesianEntityConstructor<T> constructor) {
        final var e1 = constructor.apply(1.0, 2.0);
        final var e2 = constructor.apply(2.0, -1.0);
        assertEquals(constructor.apply(3.0, 1.0), e1.sum(e2));
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testSubtract(final CartesianEntityConstructor<T> constructor) {
        final var e1 = constructor.apply(1.0, 2.0);
        final var e2 = constructor.apply(2.0, -1.0);
        assertEquals(constructor.apply(-1.0, 3.0), e1.subtract(e2));
    }

    @ParameterizedTest
    @ArgumentsSource(CartesianEntitiesProvider.class)
    void testMultiply(final CartesianEntityConstructor<T> constructor) {
        final var e1 = constructor.apply(2.0, 2.0);
        final var e2 = constructor.apply(3.0, -4.0);
        assertEquals(constructor.apply(6.0, -8.0), e1.multiply(e2));
    }

}
