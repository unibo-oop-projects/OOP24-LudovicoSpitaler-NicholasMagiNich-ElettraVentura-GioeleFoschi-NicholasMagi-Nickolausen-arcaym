package arcaym.controller.game.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import arcaym.common.geometry.impl.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.controller.game.scene.api.GameSceneInfo.CreationInfo;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link GameBuilder}.
 * It provides objects addition while leaving scene and game creation.
 */
public abstract class AbstractGameBuilder implements GameBuilder {

    private final Collection<CreationInfo> creationEvents = new ArrayList<>();
    private boolean consumed;

    /**
     * Build game scene to use for pending creations.
     * 
     * @param gameView game view
     * @return resulting scene
     */
    protected abstract GameScene buildScene(GameView gameView);

    /**
     * Create game with scene and view.
     * 
     * @param gameScene game scene
     * @param gameView game view
     * @return resulting game
     */
    protected abstract Game buildGame(GameScene gameScene, GameView gameView);

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder addObject(final GameObjectType type, final Point position) {
        if (this.consumed) {
            throw new IllegalStateException("Builder already consumed");
        }
        this.creationEvents.add(new CreationInfo(type, position));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game build(final GameView gameView) {
        Objects.requireNonNull(gameView);
        this.consumed = true;
        final var scene = this.buildScene(gameView);
        this.creationEvents.forEach(scene::scheduleCreation);
        return this.buildGame(scene, gameView);
    }

}
