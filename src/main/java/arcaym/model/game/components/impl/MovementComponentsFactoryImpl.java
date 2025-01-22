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

public class MovementComponentsFactoryImpl extends AbstractComponentsFactory implements MovementComponentsFactory {

    public MovementComponentsFactoryImpl(UniqueComponentsGameObject gameObject) {
        super(gameObject);
    }

    interface IllegalMovementConsumer {
        void reactToLimitReached(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                final Vector vel,
                final GameObject gameObject);
    }

    private boolean isWallCollisionActive(final GameSceneInfo gameScene) {
        return collisionHandler.getCollidingObjects(gameScene)
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
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
                    gameObject.setPosition(newPosition);
                } else {
                    reaction.reactToLimitReached(deltaTime, eventsScheduler, vel, gameObject);
                }
            }

        };
    }

    @Override
    public GameComponent fromInputMovement() {
        return new InputMovementComponent(gameObject);
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
}
