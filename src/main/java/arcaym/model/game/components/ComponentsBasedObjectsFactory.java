package arcaym.model.game.components;

import java.util.Collections;
import java.util.Set;

import arcaym.model.game.core.components.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.core.objects.GameObjectsFactory;
import arcaym.model.game.objects.GameObjectType;

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

        obj.setComponents(
        switch (gameObjectType) {
            case COIN
                -> Set.of(collisionFactory.collectableCollision(obj));
            case MOVING_X_OBSTACLE
                -> Set.of(collisionFactory.obstacleCollision(obj), movementFactory.automaticXMovement(obj));
            case MOVING_Y_OBSTACLE
                -> Set.of(collisionFactory.obstacleCollision(obj), movementFactory.automaticYMovement(obj));
            case SPIKE
                -> Set.of(collisionFactory.obstacleCollision(obj));
            case USER_PLAYER
                -> Set.of(movementFactory.fromInputMovement(obj));
            case WIN_GOAL
                -> Set.of(collisionFactory.reachedGoal(obj));
            default
                -> Collections.emptySet(); 
            }
            );
        return obj;
    }

}
