package arcaym.model.game.core.engine.impl;

import java.util.Objects;

import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.engine.api.GameBuilder;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.scene.impl.FactoryBasedGameScene;

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
    public GameScene buildScene() {
        return new FactoryBasedGameScene(this.gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Game buildGame(final GameScene gameScene) {
        return new SingleThreadedGame(gameScene);
    }

}
