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

        switch (gameObjectType) {
            case USER_PLAYER:
                obj.addComponent(movementFactory.fromInputMovement());
                obj.addComponent(collisionFactory.obstacleCollision());
                obj.addComponent(collisionFactory.reachedGoal());
            case MOVING_X_OBSTACLE:
                obj.addComponent(movementFactory.automaticXMovement());
            case MOVING_Y_OBSTACLE:
                obj.addComponent(movementFactory.automaticYMovement());
            case COIN:
                obj.addComponent(collisionFactory.collectableCollision());
            default:
                break;
        }
        return obj;
    }

}
