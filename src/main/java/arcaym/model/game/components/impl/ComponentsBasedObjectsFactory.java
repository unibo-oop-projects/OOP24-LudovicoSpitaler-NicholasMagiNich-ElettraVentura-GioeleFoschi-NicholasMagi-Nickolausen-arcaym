package arcaym.model.game.components.impl;

import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameObjectFactory}
 */
public class ComponentsBasedObjectsFactory implements GameObjectsFactory {
    private final double tileSize;

    public ComponentsBasedObjectsFactory(final int tileSize) {
        this.tileSize = tileSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject ofType(final GameObjectType gameObjectType) {
        var obj = new UniqueComponentsGameObject(gameObjectType, tileSize);
        CollisionComponentsFactory collisionFactory = new CollisionComponentsFactory(obj);
        MovementComponentsFactory movementFactory = new MovementComponentsFactory(obj);

        switch (gameObjectType.category()) {
            case PLAYER:
                obj.addComponent(movementFactory.fromInputMovement());
            case OBSTACLE:
                obj.addComponent(collisionFactory.obstacleCollision());
                if (gameObjectType == GameObjectType.MOVING_X_OBSTACLE) {
                    obj.addComponent(movementFactory.automaticXMovement());
                } else if (gameObjectType == GameObjectType.MOVING_Y_OBSTACLE) {
                    obj.addComponent(movementFactory.automaticYMovement());
                }
            case COLLECTABLE:
                obj.addComponent(collisionFactory.collectableCollision());
            case GOAL:
                obj.addComponent(collisionFactory.reachedGoal());
            default:
                break;
        }
        return obj;
    }

}
