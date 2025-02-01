package arcaym.model.game.core.scene.impl;

import java.util.Objects;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameScene} that uses a {@link GameObjectsFactory}.
 */
public class FactoryBasedGameScene extends AbstractGameScene {

    private final GameObjectsFactory gameObjectsFactory;

    /**
     * Initialize game scene manager with the given view and factory.
     * 
     * @param gameObjectsFactory game objects factory
     */
    public FactoryBasedGameScene(final GameObjectsFactory gameObjectsFactory) {
        super();
        this.gameObjectsFactory = Objects.requireNonNull(gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject createObject(final GameObjectType type) {
        return this.gameObjectsFactory.ofType(type);
    }

}
