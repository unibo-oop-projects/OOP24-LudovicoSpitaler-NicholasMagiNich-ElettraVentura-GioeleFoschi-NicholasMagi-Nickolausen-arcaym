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

    private final GameObjectsFactory factory;

    /**
     * Initialize with the given factory.
     * 
     * @param factory game objects factory
     */
    public FactoryBasedGameBuilder(final GameObjectsFactory factory) {
        this.factory = Objects.requireNonNull(factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene buildScene(final GameObserver gameObserver) {
        return new FactoryBasedGameScene(gameObserver, this.factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Game buildGame(final GameScene scene, final GameObserver gameObserver) {
        return new SingleThreadedGame(scene, gameObserver);
    }

}
