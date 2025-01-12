package arcaym.controller.game.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.controller.game.scene.api.GameSceneInfo.CreationInfo;
import arcaym.model.game.objects.api.GameObjectType;

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
     * @param gameObserver game observer
     * @return resulting scene
     */
    protected abstract GameScene buildScene(GameObserver gameObserver);

    /**
     * Create game with scene and observer.
     * 
     * @param gameScene game scene
     * @param gameObserver game observer
     * @return resulting game
     */
    protected abstract Game buildGame(GameScene gameScene, GameObserver gameObserver);

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder addObject(final GameObjectType gameObjectType, final Point position) {
        if (this.consumed) {
            throw new IllegalStateException("Builder already consumed");
        }
        this.creationEvents.add(new CreationInfo(gameObjectType, position));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game build(final GameObserver gameObserver) {
        Objects.requireNonNull(gameObserver);
        this.consumed = true;
        final var scene = this.buildScene(gameObserver);
        this.creationEvents.forEach(scene::scheduleCreation);
        return this.buildGame(scene, gameObserver);
    }

}
