package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.game.score.api.GameScore;
import arcaym.view.game.api.GameObserver;

/**
 * Implementation of {@link GameBuilder} that uses a {@link GameScene}.
 */
public class SceneBasedGameBuilder implements GameBuilder {

    private final GameScene scene;
    private boolean consumed;

    /**
     * Initialize with the given scene.
     * 
     * @param scene game scene
     */
    public SceneBasedGameBuilder(final GameScene scene) {
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
    public Game build(final GameObserver gameObserver, final GameScore score) {
        // re-give observer because of spotBugs
        this.consumed = true;
        this.scene.consumePendingActions();
        return new SingleThreadedGame(this.scene, gameObserver, score);
    }

}
