package arcaym.model.game.components.impl;

import java.util.stream.Stream;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangles;
import arcaym.common.geometry.impl.Vector;
import arcaym.model.game.components.api.MovementComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.scene.api.GameSceneInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of a {@link MovementComponentsFactory}.
 */
public class MovementComponentsFactoryImpl implements MovementComponentsFactory {

    /**
     * An interface for a consumer that handles the reacion to an illegal movement.
     */
    interface IllegalMovementConsumer {
        void reactToLimitReached(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                Vector vel,
                GameObject gameObject);
    }

    private boolean isWallCollisionActive(final GameSceneInfo gameScene, final UniqueComponentsGameObject gameObject) {
        return getCollidingObjects(gameScene, gameObject)
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
    }

    private Stream<GameObjectInfo> getCollidingObjects(final GameSceneInfo scene,
            final UniqueComponentsGameObject gameObject) {
        return scene.getGameObjectsInfos().stream()
                .filter(obj -> Rectangles.intersecting(obj.boundaries(), gameObject.boundaries()));
    }

    private Point nextPosition(final Vector velocity, final long deltaTime, final UniqueComponentsGameObject object) {
        final Point currentPosition = object.getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        return Point.of(newX, newY);
    }

    private GameComponent genericMovement(final Vector initialVelocity,
            final IllegalMovementConsumer reaction, final UniqueComponentsGameObject gameObject) {
        return new AbstractGameComponent(gameObject) {
            private final Vector vel = initialVelocity;

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameStateInfo gameState) {
                final Point newPosition = nextPosition(initialVelocity, deltaTime, gameObject);

                if (!isWallCollisionActive(gameScene, gameObject) && !gameState.boundaries().isOutside(newPosition)) {
                    gameObject.setPosition(newPosition);
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
    public GameComponent fromInputMovement(final UniqueComponentsGameObject gameObject) {
        return new InputMovementComponent(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticXMovement(final UniqueComponentsGameObject gameObject) {
        if (gameObject.type() == GameObjectType.MOVING_X_OBSTACLE) {
            return genericMovement(Vector.of(1, 0),
                    (deltaTime, eventsScheduler, vel, object) -> vel.invert(), gameObject);
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticYMovement(final UniqueComponentsGameObject gameObject) {
        if (gameObject.type() == GameObjectType.MOVING_Y_OBSTACLE) {
            return genericMovement(Vector.of(0, 1),
                    (deltaTime, eventsScheduler, vel, object) -> vel.invert(), gameObject);
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }
}
