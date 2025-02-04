package arcaym.model.game.core.scene;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.geometry.impl.Point;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.scene.impl.FactoryBasedGameScene;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.testing.utils.EmptyGameView;
import arcaym.testing.utils.GameTestingUtils;

class TestScene {

    private GameScene scene;

    @BeforeEach
    void setup() {
        initialize(GameTestingUtils.createObjectsFactory());
    }

    void initialize(final GameObjectsFactory factory) {
        this.scene = new FactoryBasedGameScene(factory);
    }

    void assertObjectsCount(final int expected) {
        assertEquals(expected, scene.getGameObjects().size());
    }

    void assertObjectIsPresent(final GameObjectInfo object) {
        assertTrue(scene.getGameObjectsInfos().contains(object));
    }

    void assertObjectIsMissing(final GameObjectInfo object) {
        assertFalse(scene.getGameObjectsInfos().contains(object));
    }

    GameObject getFromScene() {
        // Collection type doesn't have get
        assertObjectsCount(1);
        return scene.getGameObjects().stream().findAny().get();
    }

    @Test
    void testCreation() {
        final var type = GameObjectType.USER_PLAYER;
        final var position = Point.of(0, 0);
        assertObjectsCount(0);
        this.scene.scheduleCreation(type, position);
        assertObjectsCount(0);
        this.scene.consumePendingActions(new EmptyGameView());
        assertObjectsCount(1);
        final var gameObject = getFromScene();
        assertEquals(type, gameObject.type());
        assertEquals(position, gameObject.getPosition());
    }

    @Test
    void testDestruction() {
        final var type = GameObjectType.USER_PLAYER;
        final var position = Point.of(0, 0);
        this.scene.scheduleCreation(type, position);
        this.scene.consumePendingActions(new EmptyGameView());
        assertObjectsCount(1);
        final var gameObject = getFromScene();
        this.scene.scheduleDestruction(gameObject);
        assertObjectIsPresent(gameObject);
        this.scene.consumePendingActions(new EmptyGameView());
        assertObjectIsMissing(gameObject);
    }

}
