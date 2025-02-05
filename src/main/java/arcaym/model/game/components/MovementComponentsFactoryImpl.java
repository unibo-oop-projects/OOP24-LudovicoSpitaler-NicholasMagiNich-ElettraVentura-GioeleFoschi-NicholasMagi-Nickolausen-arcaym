package arcaym.model.game.components;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Vector;
import arcaym.model.game.core.components.AbstractGameComponent;
import arcaym.model.game.core.components.ComponentsBasedGameObject;
import arcaym.model.game.core.components.GameComponent;
import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.core.scene.GameSceneInfo;
import arcaym.model.game.events.GameEvent;

/**
 * Implementation of a {@link MovementComponentsFactory}.
 */
public class MovementComponentsFactoryImpl implements MovementComponentsFactory {

    /**
     * An interface for a consumer that handles the reacion to an illegal movement.
     */
    interface IllegalMovementHandler {
        Vector reactToLimitReached(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                Vector vel,
                GameObject gameObject);
    }

    private Point nextPosition(final Vector velocity, final long deltaTime, final ComponentsBasedGameObject object) {
        final Point currentPosition = object.getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        return Point.of(newX, newY);
    }

    private GameComponent genericMovement(final Vector initialVelocity,
            final IllegalMovementHandler reaction, final ComponentsBasedGameObject gameObject) {
        return new AbstractGameComponent(gameObject) {
            private Vector vel = initialVelocity;

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameStateInfo gameState) {
                        final Point cuurentPosition = gameObject.getPosition();
                        final Point newPosition = nextPosition(vel, deltaTime, gameObject);
                        gameObject.setPosition(newPosition);
                    if (
                        CollisionUtils.isWallCollisionActive(gameScene, gameObject) 
                        || !gameState.boundaries().contains(gameObject.boundaries())
                    ) {
                        vel = reaction.reactToLimitReached(deltaTime, eventsScheduler, vel, gameObject);
                        gameObject.setPosition(cuurentPosition);
                    }
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent fromInputMovement(final ComponentsBasedGameObject gameObject) {
        return new InputMovementComponent(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticXMovement(final ComponentsBasedGameObject gameObject) {
        return genericMovement(Vector.of(1, 0),
            (deltaTime, eventsScheduler, vel, object) -> vel.invert(), gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent automaticYMovement(final ComponentsBasedGameObject gameObject) {
        return genericMovement(Vector.of(0, 1),
            (deltaTime, eventsScheduler, vel, object) -> vel.invert(), gameObject);
    }
}
