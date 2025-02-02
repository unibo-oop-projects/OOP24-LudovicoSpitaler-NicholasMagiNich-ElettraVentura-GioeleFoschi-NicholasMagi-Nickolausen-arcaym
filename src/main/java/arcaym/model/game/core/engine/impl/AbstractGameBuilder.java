package arcaym.model.game.core.engine.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.impl.Point;
import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.engine.api.GameBuilder;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.scene.api.GameSceneInfo.CreationInfo;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Abstract implementation of {@link GameBuilder}.
 * It provides objects addition while leaving scene and game creation.
 */
public abstract class AbstractGameBuilder implements GameBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGameBuilder.class);

    private final Collection<CreationInfo> creationEvents = new ArrayList<>();
    private boolean consumed;

    /**
     * Build game scene to use for pending creations.
     * 
     * @return resulting scene
     */
    protected abstract GameScene buildScene();

    /**
     * Create game with scene.
     * 
     * @param gameScene game scene
     * @return resulting game
     */
    protected abstract Game buildGame(GameScene gameScene);

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBuilder addObject(final GameObjectType type, final Point position) {
        if (this.consumed) {
            throw new IllegalStateException("Builder already consumed");
        }
        final var event = new CreationInfo(type, position);
        this.creationEvents.add(event);
        LOGGER.info(new StringBuilder("Registered creation event ").append(event).toString());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game build() {
        this.consumed = true;
        final var scene = this.buildScene();
        this.creationEvents.forEach(scene::scheduleCreation);
        LOGGER.info("Finished scheduling all creation events");
        return this.buildGame(scene);
    }

}
