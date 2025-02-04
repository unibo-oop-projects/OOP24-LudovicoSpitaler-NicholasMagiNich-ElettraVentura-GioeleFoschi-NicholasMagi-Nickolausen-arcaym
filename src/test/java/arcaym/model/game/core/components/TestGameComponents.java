package arcaym.model.game.core.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.engine.impl.DefaultGameState;
import arcaym.model.game.core.events.api.EventsManager;
import arcaym.model.game.core.events.impl.ThreadSafeEventsManager;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.core.scene.api.GameSceneInfo;
import arcaym.model.game.core.scene.impl.FactoryBasedGameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.utils.GameTestingUtils;

class TestGameComponents {
    private final static int MIN_COORDINATE = 0;
    private final static int MAX_COORDINATE = 3;
    private final static int DELTA_TIME = 1;
    private GameObjectsFactory gameObjFactory;
    private EventsManager<GameEvent> gameEventsManager;
    private EventsManager<InputEvent> inputEventsManager;
    private GameSceneInfo gameScene;
    private GameStateInfo gameState;

    @BeforeEach
    void setUp(){
        this.gameObjFactory = GameTestingUtils.createObjectsFactory(); 
        this.gameEventsManager = new ThreadSafeEventsManager<>();
        this.inputEventsManager = new ThreadSafeEventsManager<>();
        this.gameScene = new FactoryBasedGameScene(gameObjFactory);
        this.gameState = new DefaultGameState(
            new Rectangle(Point.of(MIN_COORDINATE, MIN_COORDINATE), Point.of(MAX_COORDINATE, MAX_COORDINATE))
            );

    }

    @Test
    void TestAutomaticMovement(){
        final GameObject enemy = gameObjFactory.ofType(GameObjectType.MOVING_X_OBSTACLE);
        final var pos1 = Point.of(0, 0);
        final var pos2 = Point.of(1, 0);
        final var pos3 = Point.of(2, 0);
        final var pos4 = Point.of(3, 0);
        enemy.setPosition(pos1);
        assertEquals(enemy.getPosition(), pos1);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos2);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos3);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos4);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos3);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos2);
    }

    @Test
    void TestInputMovement(){
        final GameObject player = gameObjFactory.ofType(GameObjectType.USER_PLAYER);
        final var pos1 = Point.of(0, 0);
        final var pos2 = Point.of(1, 0);
        final var pos3 = Point.of(2, 0);
        final var pos4 = Point.of(3, 0);
        player.setup(gameEventsManager, inputEventsManager, gameScene, gameState);
        player.setPosition(pos1);
        assertEquals(player.getPosition(), pos1);
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.RIGHT,false));
        this.inputEventsManager.consumePendingEvents();
        updateObject(player);
        assertEquals(player.getPosition(), pos2);
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.RIGHT,false));
        this.inputEventsManager.consumePendingEvents();
        updateObject(player);
        assertEquals(player.getPosition(), pos3);
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.RIGHT,false));
        this.inputEventsManager.consumePendingEvents();
        updateObject(player);
        assertEquals(player.getPosition(), pos4);
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.RIGHT,false));
        this.inputEventsManager.consumePendingEvents();
        updateObject(player);
        assertEquals(player.getPosition(), pos4);
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.RIGHT,true));
        this.inputEventsManager.scheduleEvent(new InputEvent(InputType.LEFT,false));
        this.inputEventsManager.consumePendingEvents();
        updateObject(player);
        assertEquals(player.getPosition(), pos3);
    }

    @Test
    void TestCollision(){
        final GameObject player = gameObjFactory.ofType(GameObjectType.USER_PLAYER);
        final GameObject enemy = gameObjFactory.ofType(GameObjectType.MOVING_X_OBSTACLE);
        final var pos1 = Point.of(0, 0);
        final var pos2 = Point.of(1, 0);
        final var pos3 = Point.of(2, 0);
        player.setPosition(pos3);
        enemy.setPosition(pos1);
        assertEquals(player.getPosition(), pos3);
        assertEquals(enemy.getPosition(), pos1);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos2);
        updateObject(enemy);
        assertEquals(enemy.getPosition(), pos3);
        assertEquals(player.getPosition(), pos3);
        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, e -> assertEquals(e, GameEvent.GAME_OVER));
        this.gameEventsManager.consumePendingEvents();
    }
    
    private void updateObject(final GameObject object) {
        object.update(DELTA_TIME, this.gameEventsManager, this.gameScene, this.gameState);
    }    
}
