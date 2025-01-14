package arcaym.model.game.components.impl;

import java.util.function.Predicate;

import arcaym.common.point.api.Point;
import arcaym.common.shapes.api.Rectangle;
import arcaym.common.shapes.impl.Rectangles;
import arcaym.common.vector.api.Vector;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
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

    interface IllegalMovementConsumer {
        void handleCollisionWithBlock(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, Vector vel,
                GameObject gameObject);
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

        };
    }

    private boolean areRectanglesColliding(Rectangle rect1, Rectangle rect2) {
        return Rectangles.intersecting(rect1, rect2);
    }

    private boolean isThereACollision(Point point) {
        return gameObject.boundaries().isInside(point);
    }

    @Override
    public GameComponent obstacleCollision() {
        if (gameObject.type() == GameObjectType.USER_PLAYER) {
            return genericCollision(info -> info.category() == GameObjectCategory.OBSTACLE,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
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

    private GameComponent genericMovement(Vector initialVelocity,
            IllegalMovementConsumer reaction) {
        return new AbstractGameComponent(gameObject) {
            private Vector vel = initialVelocity;

            @Override
            public void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, GameSceneInfo gameScene,
                    GameState gameState) {
                Point currentPosition = gameObject.getPosition();
                double newX = currentPosition.x() + (vel.getX() * deltaTime);
                double newY = currentPosition.y() + (vel.getY() * deltaTime);
                Point newPosition = Point.of((int) Math.round(newX), (int) Math.round(newY));

                if (isWallCollisionActive(gameObject, gameScene)) {
                    gameObject.setPosition(newPosition);
                } else {
                    reaction.handleCollisionWithBlock(deltaTime, eventsScheduler, vel, gameObject);
                }
            }

        };
    }

    @Override
    public GameComponent fromKeyBoardMovement() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromKeyBoardMovement'");
    }

    private boolean isWallCollisionActive(UniqueComponentsGameObject gameObject, GameSceneInfo gameScene) {
        return gameScene.getGameObjectsInfos().stream()
                .filter(infos -> areRectanglesColliding(infos.boundaries().drawArea(),
                        gameObject.boundaries().drawArea()))
                .filter(infos -> infos.boundaries().surface().anyMatch(point -> isThereACollision(point)))
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
    }

    @Override
    public GameComponent automaticXMovement() {
        if (gameObject.type() == GameObjectType.MOVING_X_OBSTACLE) {
            return genericMovement(Vector.of(1, 0),
                    (deltaTime, eventsScheduler, vel, gameObject) -> vel.invert());
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    @Override
    public GameComponent automaticYMovement() {
        if (gameObject.type() == GameObjectType.MOVING_Y_OBSTACLE) {
            return genericMovement(Vector.of(0, 1),
                    (deltaTime, eventsScheduler, vel, gameObject) -> vel.invert());
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    @Override
    public GameComponent reachedGoal() {
        if (gameObject.type() == GameObjectType.USER_PLAYER) {
            return genericCollision(info -> info.category() == GameObjectCategory.GOAL,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }
}
