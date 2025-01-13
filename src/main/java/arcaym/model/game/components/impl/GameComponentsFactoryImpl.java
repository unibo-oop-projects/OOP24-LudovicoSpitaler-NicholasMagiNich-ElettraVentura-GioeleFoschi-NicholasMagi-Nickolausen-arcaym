package arcaym.model.game.components.impl;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import arcaym.common.point.api.Point;
import arcaym.common.point.impl.Points;
import arcaym.common.shapes.api.Rectangle;
import arcaym.common.shapes.impl.Rectangles;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
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
    private final UniqueComponentsGameObject gameObject;

    public GameComponentsFactoryImpl(UniqueComponentsGameObject gameObject) {
        this.gameObject = gameObject;
    }

    interface CollisionConsumer {
        void reactToCollision(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                GameObjectInfo collidingObject, GameSceneInfo gameScene);
    }

    private GameComponent genericCollision(Predicate<GameObjectInfo> predicateFromObjectInfo,
            CollisionConsumer reaction) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, GameSceneInfo gameScene,
                    GameState gameState) {
                gameScene.getGameObjectsInfos().stream()
                        .filter(infos -> predicateFromObjectInfo.test(infos))
                        .filter(infos -> areRectanglesColliding(infos.boundaries().drawArea(),
                                gameObject.boundaries().drawArea()))
                        .filter(infos -> infos.boundaries().surface().anyMatch(point -> isThereACollision(point)))
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj, gameScene));
            }

            private boolean areRectanglesColliding(Rectangle rect1, Rectangle rect2) {
                return Rectangles.intersecting(rect1, rect2);
            }

        };
    }

    private boolean isThereACollision(Point point) {
        return gameObject.boundaries().isInside(point);
    }

    private GameComponent objectTypeCollision(Predicate<GameObjectType> predicateFromType,
            CollisionConsumer reaction) {
        return genericCollision(obj -> predicateFromType.test(obj.type()), reaction);

    }

    @Override
    public GameComponent obstacleCollision() {
        if (gameObject.type().equals(GameObjectType.USER_PLAYER)) {
            return genericCollision(info -> info.category() == GameObjectCategory.OBSTACLE,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    @Override
    public GameComponent wallCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromKeyBoardMovement'");
    }

    @Override
    public GameComponent coinCollision() {
        if (gameObject.type().equals(GameObjectType.COIN)) {
            return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.INCREMENT_SCORE);
                        gameScene.scheduleDestruction(gameObject);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    @Override
    public GameComponent fromKeyBoardMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromKeyBoardMovement'");
    }

    @Override
    public GameComponent automaticXMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'automaticXMovement'");
    }

    @Override
    public GameComponent automaticYMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'automaticYMovement'");
    }

    @Override
    public GameComponent reachedGoal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reachedGoal'");

    }
}
