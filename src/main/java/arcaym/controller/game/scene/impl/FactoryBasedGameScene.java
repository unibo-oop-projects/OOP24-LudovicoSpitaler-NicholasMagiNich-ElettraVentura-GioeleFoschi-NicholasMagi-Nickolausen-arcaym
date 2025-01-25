package arcaym.controller.game.scene.impl;

import java.util.Objects;

import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameScene} that uses a {@link GameObjectsFactory}.
 */
public class FactoryBasedGameScene extends AbstractGameScene {

    private final GameObjectsFactory gameObjectsFactory;

    /**
     * Initialize game scene manager with the given view and factory.
     * 
     * @param gameView game view
     * @param gameObjectsFactory game objects factory
     */
    public FactoryBasedGameScene(final GameView gameView, final GameObjectsFactory gameObjectsFactory) {
        super(gameView);
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
