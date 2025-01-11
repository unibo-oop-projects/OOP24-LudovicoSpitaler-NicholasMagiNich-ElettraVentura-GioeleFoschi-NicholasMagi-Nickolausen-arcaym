package arcaym.controller.game.core.scene.impl;

import java.util.Objects;

import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameObserver;

/**
 * Implementation of {@link GameScene} that uses a {@link GameObjectFactory}.
 */
public class FactoryBasedGameSceneManager extends AbstractGameSceneManager {

    private final GameObjectFactory factory;

    /**
     * Initialize game scene manager with the given observer and factory.
     * 
     * @param gameObserver game observer
     * @param factory game objects factory
     */
    public FactoryBasedGameSceneManager(final GameObserver gameObserver, final GameObjectFactory factory) {
        super(gameObserver);
        this.factory = Objects.requireNonNull(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject createInstance(final GameObjectType type) {
        return this.factory.ofType(type);
    }

}
