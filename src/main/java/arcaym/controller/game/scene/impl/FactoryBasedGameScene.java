package arcaym.controller.game.scene.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameScene} that uses a {@link GameObjectsFactory}.
 */
public class FactoryBasedGameScene extends AbstractGameScene {

    private final GameObjectsFactory gameObjectsFactory;

    /**
     * Initialize game scene manager with the given observer and factory.
     * 
     * @param gameObserver game observer
     * @param gameObjectsFactory game objects factory
     */
    public FactoryBasedGameScene(final GameObserver gameObserver, final GameObjectsFactory gameObjectsFactory) {
        super(gameObserver);
        this.gameObjectsFactory = Objects.requireNonNull(gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject createInstance(final GameObjectType gameObjectType) {
        return this.gameObjectsFactory.ofType(gameObjectType);
    }

}
