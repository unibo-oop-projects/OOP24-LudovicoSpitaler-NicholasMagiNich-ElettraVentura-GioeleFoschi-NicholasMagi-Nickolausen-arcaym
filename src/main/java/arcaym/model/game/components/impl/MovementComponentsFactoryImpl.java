package arcaym.model.game.components.impl;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.components.api.MovementComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of a {@link MovementComponentsFactory}.
 */
public class MovementComponentsFactoryImpl extends AbstractComponentsFactory implements MovementComponentsFactory {

    /**
     * Contructor with gameobject as an argument.
     * 
     * @param gameObject
     */
    public MovementComponentsFactoryImpl(final UniqueComponentsGameObject gameObject) {
        super(gameObject);
    }

    interface IllegalMovementConsumer {
        void reactToLimitReached(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                Vector vel,
                GameObject gameObject);
    }

    private boolean isWallCollisionActive(final GameSceneInfo gameScene) {
        return getCollisionHandler().getCollidingObjects(gameScene)
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
    }

    private GameComponent genericMovement(final Vector initialVelocity,
            final IllegalMovementConsumer reaction) {
        return new AbstractGameComponent(getGameObject()) {
            private final Vector vel = initialVelocity;

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                final Point newPosition = getMovementHandler().nextPosition(initialVelocity, deltaTime);

                if (!isWallCollisionActive(gameScene)) {
                    getGameObject().setPosition(newPosition);
                } else {
                    reaction.reactToLimitReached(deltaTime, eventsScheduler, vel, getGameObject());
                }
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent fromInputMovement() {
        return new InputMovementComponent(getGameObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticXMovement() {
        if (getGameObject().type() == GameObjectType.MOVING_X_OBSTACLE) {
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
        if (getGameObject().type() == GameObjectType.MOVING_Y_OBSTACLE) {
            return genericMovement(Vector.of(0, 1),
                    (deltaTime, eventsScheduler, vel, gameObject) -> vel.invert());
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }
}
