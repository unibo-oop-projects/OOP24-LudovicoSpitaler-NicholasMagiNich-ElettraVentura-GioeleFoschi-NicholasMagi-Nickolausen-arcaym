package arcaym.controller.game.core.scene.impl;

import java.util.Objects;

import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameSceneManager} that uses a {@link GameObjectFactory}.
 */
public class FactoryBasedGameSceneManager extends AbstractGameSceneManager {

    private final GameObjectFactory factory;

    /**
     * Initialize game scene manager with the given view and factory.
     * 
     * @param view game view
     * @param factory game objects factory
     */
    public FactoryBasedGameSceneManager(final GameView view, final GameObjectFactory factory) {
        super(view);
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
