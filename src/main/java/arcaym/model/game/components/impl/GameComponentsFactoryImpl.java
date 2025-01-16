package arcaym.model.game.components.impl;

import java.util.function.Predicate;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
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
import arcaym.model.game.logics.api.CollisionHandler;
import arcaym.model.game.logics.api.MovementHandler;
import arcaym.model.game.logics.impl.BasicCollisionHandler;
import arcaym.model.game.logics.impl.BasicMovementHandler;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Basic implementation of {@link GameComponentsFactory}.
 */
public class GameComponentsFactoryImpl implements GameComponentsFactory {
    private final UniqueComponentsGameObject gameObject;
    private final CollisionHandler collisionHandler;
    private final MovementHandler movementHandler;

    public GameComponentsFactoryImpl(final UniqueComponentsGameObject gameObject) {
        this.gameObject = gameObject;
        this.collisionHandler = new BasicCollisionHandler(gameObject);
        this.movementHandler = new BasicMovementHandler(gameObject);
    }

    interface CollisionConsumer {
        void reactToCollision(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                final GameObjectInfo collidingObject, final GameSceneInfo gameScene);
    }

    interface IllegalMovementConsumer {
        void reactToLimitReached(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                final Vector vel,
                final GameObject gameObject);
    }

    private GameComponent genericCollision(final Predicate<GameObjectInfo> predicateFromObjectInfo,
            final CollisionConsumer reaction) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                collisionHandler.getCollidingObjects(gameScene)
                        .filter(infos -> predicateFromObjectInfo.test(infos))
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj, gameScene));
            }

        };
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent collectableCollision() {
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

    private GameComponent genericMovement(final Vector initialVelocity,
            IllegalMovementConsumer reaction) {
        return new AbstractGameComponent(gameObject) {
            private Vector vel = initialVelocity;

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                Point newPosition = movementHandler.nextPosition(initialVelocity, deltaTime);

                if (!isWallCollisionActive(gameScene)) {
                    movementHandler.updatePosition(newPosition);
                } else {
                    reaction.reactToLimitReached(deltaTime, eventsScheduler, vel, gameObject);
                }
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent fromInputMovement() {
        return new InputMovementComponent(gameObject);
    }

    private boolean isWallCollisionActive(final GameSceneInfo gameScene) {
        return collisionHandler.getCollidingObjects(gameScene)
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticXMovement() {
        if (gameObject.type() == GameObjectType.MOVING_X_OBSTACLE) {
            return genericMovement(Vector.of(1, 0),
                    (deltaTime, eventsScheduler, vel, gameObject) -> vel.invert());
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticYMovement() {
        if (gameObject.type() == GameObjectType.MOVING_Y_OBSTACLE) {
            return genericMovement(Vector.of(0, 1),
                    (deltaTime, eventsScheduler, vel, gameObject) -> vel.invert());
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent reachedGoal() {
        return genericCollision(info -> info.category() == GameObjectCategory.GOAL,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                });
    }
}
