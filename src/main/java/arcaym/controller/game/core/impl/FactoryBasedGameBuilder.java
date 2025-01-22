package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.controller.game.scene.impl.FactoryBasedGameScene;
import arcaym.model.game.core.objects.api.GameObjectsFactory;

/**
 * Implementation of {@link GameBuilder} that uses a {@link GameObjectsFactory}.
 */
public class FactoryBasedGameBuilder extends AbstractGameBuilder {

    private final GameObjectsFactory gameObjectsFactory;

    /**
     * Initialize with the given factory.
     * 
     * @param gameObjectsFactory game objects factory
     */
    public FactoryBasedGameBuilder(final GameObjectsFactory gameObjectsFactory) {
        this.gameObjectsFactory = Objects.requireNonNull(gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene buildScene(final GameObserver gameObserver) {
        return new FactoryBasedGameScene(gameObserver, this.gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Game buildGame(final GameScene gameScene, final GameObserver gameObserver) {
        return new SingleThreadedGame(gameScene, gameObserver);
    }

}
