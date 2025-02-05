package arcaym.model.game.components.impl;

import arcaym.model.game.components.api.CollisionComponentsFactory;
import arcaym.model.game.components.api.MovementComponentsFactory;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameObjectsFactory} using game components.
 */
public class ComponentsBasedObjectsFactory implements GameObjectsFactory {
    private final int tileSize;
    private final CollisionComponentsFactory collisionFactory = new CollisionComponentsFactoryImpl();
    private final MovementComponentsFactory movementFactory = new MovementComponentsFactoryImpl();

    /**
     * Contructor receiving tile size as an argument.
     * 
     * @param tileSize tile size
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

        switch (gameObjectType) {
            case COIN:
                obj.addComponent(collisionFactory.collectableCollision(obj));
                break;
            case FLOOR:
                break;
            case MOVING_X_OBSTACLE:
                obj.addComponent(collisionFactory.obstacleCollision(obj));
                obj.addComponent(movementFactory.automaticXMovement(obj));
                break;
            case MOVING_Y_OBSTACLE:
                obj.addComponent(collisionFactory.obstacleCollision(obj));
                obj.addComponent(movementFactory.automaticYMovement(obj));
                break;
            case SPIKE:
                obj.addComponent(collisionFactory.obstacleCollision(obj));
                break;
            case USER_PLAYER:
                obj.addComponent(movementFactory.fromInputMovement(obj));
                break;
            case WALL:
                break;
            case WIN_GOAL:
                obj.addComponent(collisionFactory.reachedGoal(obj));
                break;
            default:
                break;

        }
        return obj;
    }

}
