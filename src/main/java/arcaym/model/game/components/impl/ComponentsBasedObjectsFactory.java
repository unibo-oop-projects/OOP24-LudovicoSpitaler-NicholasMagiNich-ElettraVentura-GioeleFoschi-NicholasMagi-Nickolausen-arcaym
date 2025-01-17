package arcaym.model.game.components.impl;

import arcaym.model.game.components.api.GameComponentsFactory;
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
        GameComponentsFactory componentsProvider = new GameComponentsFactoryImpl(obj);

        switch (gameObjectType) {
            case USER_PLAYER:
                obj.addComponent(componentsProvider.fromInputMovement());
                obj.addComponent(componentsProvider.obstacleCollision());
                obj.addComponent(componentsProvider.reachedGoal());
            case MOVING_X_OBSTACLE:
                obj.addComponent(componentsProvider.automaticXMovement());
            case MOVING_Y_OBSTACLE:
                obj.addComponent(componentsProvider.automaticYMovement());
            case COIN:
                obj.addComponent(componentsProvider.collectableCollision());
            default:
                break;
        }
        return obj;
    }

}
