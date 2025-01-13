package arcaym.model.game.components.impl;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import arcaym.common.point.impl.Points;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.api.ComponentsBasedObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Basic implementation of {@link GameComponentsFactory}.
 */
public class GameComponentsFactoryImpl implements GameComponentsFactory {
    private final ComponentsBasedObject gameObject;

    public GameComponentsFactoryImpl(ComponentsBasedObject gameObject) {
        this.gameObject = gameObject;
    }

    interface CollisionConsumer {
        void reactToCollision(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                GameObjectInfo collidingObject);
    }

    private GameComponent genericCollision(Predicate<GameObjectInfo> predicateFromObjectInfo,
            CollisionConsumer reaction) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void setup(EventsSubscriber<GameEvent> gameEventsSubscriber,
                    EventsSubscriber<InputEvent> inputEventsSubscriber, GameSceneInfo gameScene, GameState gameState) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'setup'");
            }

            @Override
            public void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, GameSceneInfo gameScene,
                    GameState gameState) {
                gameScene.getGameObjectsInfos().stream()
                        .filter(infos -> predicateFromObjectInfo.test(infos))
                        .filter(infos -> Points.distance(infos.getPosition(), gameObject.getPosition()) == 0)
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj));

            }

        };
    }

    private GameComponent objectTypeCollision(Predicate<GameObjectType> predicateFromType,
            CollisionConsumer reaction) {
        return genericCollision(obj -> predicateFromType.test(obj.type()), reaction);

    }

    @Override
    public GameComponent obstacleCollision() {
        if (gameObject.type().equals(GameObjectType.USER_PLAYER)) {
            return genericCollision(info -> info.category().equals(GameObjectCategory.OBSTACLE),
                    (deltaTime, eventsScheduler, collidingObject) -> {
                        eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    @Override
    public GameComponent wallCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'wallCollision'");
    }

    @Override
    public GameComponent coinCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'coinCollision'");
    }

    @Override
    public GameComponent fromKeyBoardMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromKeyBoardMovement'");
    }

    @Override
    public GameComponent automaticMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'automaticMovement'");
    }
}
