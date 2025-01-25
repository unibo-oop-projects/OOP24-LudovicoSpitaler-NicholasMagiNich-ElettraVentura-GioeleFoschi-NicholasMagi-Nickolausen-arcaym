package arcaym.model.game.components.impl;

import java.util.stream.Stream;

import arcaym.model.game.components.api.CollisionComponentsFactory;
import arcaym.model.game.components.api.MovementComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
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

    private Stream<GameComponent> obstacleComponents(final UniqueComponentsGameObject gameObject) {
        return Stream.of(collisionFactory.obstacleCollision(gameObject));
    }

    private Stream<GameComponent> movingXobstacleComponents(final UniqueComponentsGameObject gameObject) {
        return Stream.concat(obstacleComponents(gameObject), Stream.of(movementFactory.automaticXMovement(gameObject)));
    }

    private Stream<GameComponent> movingYobstacleComponents(final UniqueComponentsGameObject gameObject) {
        return Stream.concat(obstacleComponents(gameObject), Stream.of(movementFactory.automaticYMovement(gameObject)));
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
                movingXobstacleComponents(obj).forEach(comp -> obj.addComponent(comp));
                break;
            case MOVING_Y_OBSTACLE:
                movingYobstacleComponents(obj).forEach(comp -> obj.addComponent(comp));
                break;
            case SPIKE:
                obstacleComponents(obj).forEach(comp -> obj.addComponent(comp));
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
