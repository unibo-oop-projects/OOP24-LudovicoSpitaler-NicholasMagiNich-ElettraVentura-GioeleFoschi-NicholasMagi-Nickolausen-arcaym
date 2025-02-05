package arcaym.model.game.core.engine;

import java.util.Objects;

import arcaym.common.geometry.Rectangle;
import arcaym.model.game.core.objects.GameObjectsFactory;
import arcaym.model.game.core.scene.GameScene;
import arcaym.model.game.core.scene.FactoryBasedGameScene;

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
    protected GameScene buildScene() {
        return new FactoryBasedGameScene(this.gameObjectsFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Game buildGame(final GameScene gameScene, final Rectangle boundaries) {
        return new SingleThreadedGame(gameScene, boundaries);
    }

}
