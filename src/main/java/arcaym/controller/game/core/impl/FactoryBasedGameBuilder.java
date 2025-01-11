package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.controller.game.core.scene.impl.FactoryBasedGameSceneManager;
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameBuilder} that uses a {@link GameObjectFactory}.
 */
public class FactoryBasedGameBuilder implements GameBuilder {

    private final GameSceneManager scene;
    private final GameView view;
    private boolean consumed = false;

    /**
     * Initialize with the given view and factory.
     * 
     * @param view game view
     * @param factory game objects factory
     */
    public FactoryBasedGameBuilder(final GameView view, final GameObjectFactory factory) {
        this.view = Objects.requireNonNull(view);
        this.scene = new FactoryBasedGameSceneManager(view, factory);
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
    public Game build() {
        this.consumed = true;
        return new SingleThreadedGame(this.scene, this.view);
    }

}
