package arcaym.model.game.core.engine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.core.engine.api.GameBuilder;
import arcaym.model.game.core.engine.impl.FactoryBasedGameBuilder;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.testing.utils.GameTestingUtils;

class TestGameBuilder {

    private static final int MIN_COORDINATE = 1;
    private static final int MAX_COORDINATE = 3;

    private GameBuilder builder;

    @BeforeEach
    void setup() {
        this.builder = new FactoryBasedGameBuilder(GameTestingUtils.createObjectsFactory());
        this.builder.addObject(GameObjectType.USER_PLAYER, Point.of(MIN_COORDINATE, MIN_COORDINATE));
        this.builder.addObject(GameObjectType.COIN, Point.of(MAX_COORDINATE, MIN_COORDINATE));
        this.builder.addObject(GameObjectType.FLOOR, Point.of(MIN_COORDINATE, MAX_COORDINATE));
        this.builder.addObject(GameObjectType.SPIKE, Point.of(MAX_COORDINATE, MAX_COORDINATE));
    }

    void assertBoundariesBigEnough(final Rectangle boundaries) {
        assertDoesNotThrow(() -> this.builder.build(boundaries));
    }

    void assertBoundariesNotEnough(final Rectangle boundaries) {
        assertThrows(IllegalArgumentException.class, () -> this.builder.build(boundaries));
    }

    @Test
    void testBigEnough() {
        assertBoundariesBigEnough(Rectangle.of(
            Point.of(MIN_COORDINATE - 1, MIN_COORDINATE - 1),
            Point.of(MAX_COORDINATE + 1, MAX_COORDINATE + 1)
        ));
        assertBoundariesBigEnough(Rectangle.of(
            Point.of(MIN_COORDINATE, MIN_COORDINATE),
            Point.of(MAX_COORDINATE, MAX_COORDINATE)
        ));
    }

    @Test
    void testNotEnough() {
        assertBoundariesNotEnough(Rectangle.of(
            Point.of(MIN_COORDINATE + 1, MIN_COORDINATE + 1),
            Point.of(MAX_COORDINATE - 1, MAX_COORDINATE - 1)
        ));
    }

}
