package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameBuilder} that uses a {@link GameSceneManager}.
 */
public class SceneBasedGameBuilder implements GameBuilder {

    private final GameSceneManager scene;
    private boolean consumed;

    /**
     * Initialize with the given view and factory.
     * 
     * @param scene game view
     */
    public SceneBasedGameBuilder(final GameSceneManager scene) {
        this.scene = Objects.requireNonNull(scene);
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
    public Game build(final GameView view) {
        this.consumed = true;
        this.scene.consumePendingActions();
        return new SingleThreadedGame(this.scene, view);
    }

}
