package arcaym.model.game.components.impl;

import arcaym.model.game.components.api.CollisionComponentsFactory;
import arcaym.model.game.components.api.MovementComponentsFactory;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameObjectFactory}.
 */
public class ComponentsBasedObjectsFactory implements GameObjectsFactory {
    private final double tileSize;
    final CollisionComponentsFactory collisionFactory = new CollisionComponentsFactoryImpl();
    final MovementComponentsFactory movementFactory = new MovementComponentsFactoryImpl();

    /**
     * Contructor receiving tileSize as an argument.
     * 
     * @param tileSize
     */
    public ComponentsBasedObjectsFactory(final int tileSize) {
        this.tileSize = tileSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject ofType(final GameObjectType gameObjectType) {
        final var obj = new UniqueComponentsGameObject(gameObjectType, tileSize);

        switch (gameObjectType.category()) {
            case PLAYER:
                obj.addComponent(movementFactory.fromInputMovement(obj));
                break;
            case OBSTACLE:
                obj.addComponent(collisionFactory.obstacleCollision(obj));
                if (gameObjectType == GameObjectType.MOVING_X_OBSTACLE) {
                    obj.addComponent(movementFactory.automaticXMovement(obj));
                } else if (gameObjectType == GameObjectType.MOVING_Y_OBSTACLE) {
                    obj.addComponent(movementFactory.automaticYMovement(obj));
                }
                break;
            case COLLECTABLE:
                obj.addComponent(collisionFactory.collectableCollision(obj));
                break;
            case GOAL:
                obj.addComponent(collisionFactory.reachedGoal(obj));
                break;
            default:
                break;
        }
        return obj;
    }

}
