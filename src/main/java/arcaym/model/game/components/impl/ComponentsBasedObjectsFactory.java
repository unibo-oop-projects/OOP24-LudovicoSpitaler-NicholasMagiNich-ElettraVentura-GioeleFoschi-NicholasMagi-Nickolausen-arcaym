package arcaym.model.game.components.impl;

import java.util.function.Function;

import arcaym.common.geometry.impl.Point;
import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.BoundariesFactory;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBoundaries;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.core.objects.impl.BoundariesFactoryImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameObjectFactory}
 */
public class ComponentsBasedObjectsFactory implements GameObjectsFactory {
    private BoundariesFactory boundariesFactory;

    public ComponentsBasedObjectsFactory(final int tileSize) {
        this.boundariesFactory = new BoundariesFactoryImpl(tileSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject ofType(final GameObjectType gameObjectType) {
        var obj = new UniqueComponentsGameObject(gameObjectType, boundariesOfType(gameObjectType));
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

    private Function<Point, GameObjectBoundaries> boundariesOfType(final GameObjectType gameObjectType) {
        return switch (gameObjectType.category()) {
            case BLOCK -> this.boundariesFactory::squareBoundaries;
            case COLLECTABLE -> this.boundariesFactory::circularBoundaries;
            case GOAL -> this.boundariesFactory::squareBoundaries;
            case OBSTACLE -> this.boundariesFactory::circularBoundaries;
            case PLAYER -> this.boundariesFactory::circularBoundaries;
        };
    }

}
