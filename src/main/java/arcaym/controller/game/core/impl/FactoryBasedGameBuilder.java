package arcaym.controller.game.core.impl;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.controller.game.scene.impl.FactoryBasedGameScene;
import arcaym.model.game.core.objects.api.GameObjectsFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.game.score.api.GameScore;

/**
 * Implementation of {@link GameBuilder} that uses a {@link GameObjectsFactory}.
 */
public class FactoryBasedGameBuilder implements GameBuilder {

    private final GameScene scene;
    private boolean consumed;

    /**
     * Initialize with the given factory and observer.
     * 
     * @param gameObserver game observer
     * @param factory game objects factory
     */
    public FactoryBasedGameBuilder(final GameObserver gameObserver, final GameObjectsFactory factory) {
        this.scene = new FactoryBasedGameScene(gameObserver, factory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder addObject(final GameObjectType type, final Point position) {
        if (this.consumed) {
            throw new IllegalStateException("Builder already consumed");
        }

        this.scene.scheduleCreation(type, position);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game build(final GameObserver gameObserver, final GameScore score) {
        // re-give observer because of spotBugs
        this.consumed = true;
        this.scene.consumePendingActions();
        return new SingleThreadedGame(this.scene, gameObserver, score);
    }

}
