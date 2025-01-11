package arcaym.model.game.core.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.GameObjectsProvider.GameObjectConstructor;
import arcaym.model.game.objects.api.GameObjectType;

class TestGameObject {

    @ParameterizedTest
    @ArgumentsSource(GameObjectsProvider.class)
    void testPosition(final GameObjectConstructor constructor, final GameObjectType type) {
        final var gameObject = constructor.apply(type);
        assertEquals(gameObject.getPosition(), Point.zero());
        final var position = Point.of(2, 3);
        gameObject.setPosition(position);
        assertEquals(gameObject.getPosition(), position);
    }

    @ParameterizedTest
    @ArgumentsSource(GameObjectsProvider.class)
    void testMove(final GameObjectConstructor constructor, final GameObjectType type) {
        final var gameObject = constructor.apply(type);
        assertEquals(gameObject.getPosition(), Point.zero());
        final var distance1 = Point.of(2, 3);
        gameObject.move(distance1);
        assertEquals(gameObject.getPosition(), distance1);
        final var distance2 = Point.of(-1, 2);
        gameObject.move(distance2);
        assertEquals(gameObject.getPosition(), distance1.sum(distance2));
    }

}
