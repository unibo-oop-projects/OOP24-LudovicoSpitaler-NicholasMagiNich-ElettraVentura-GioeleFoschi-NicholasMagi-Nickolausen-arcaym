package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.core.events.api.EventsManager;
import arcaym.controller.game.core.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.score.api.GameScore;
import arcaym.model.game.score.api.GameScoreView;

/**
 * Abstract implementation of {@link Game}.
 * It provides events management while leaving the update logic.
 */
public abstract class AbstractThreadSafeGame implements Game {

    private final EventsManager<GameEvent> gameEventsManager = new ThreadSafeEventsManager<>();
    private final EventsManager<InputEvent> inputEventsManager = new ThreadSafeEventsManager<>();
    private final GameScene scene;
    private final GameScore score;

    /**
     * Initialize with the given scene and game observer.
     * 
     * @param scene game scene manager
     * @param gameObserver game observer
     * @param score game score
     */
    protected AbstractThreadSafeGame(
        final GameScene scene, 
        final GameObserver gameObserver,
        final GameScore score
    ) {
        Objects.requireNonNull(gameObserver);
        this.scene = Objects.requireNonNull(scene);
        this.score = Objects.requireNonNull(score);

        gameObserver.registerEventsCallbacks(this.gameEventsManager);
        score.registerEventsCallbacks(this.gameEventsManager);
        scene.gameObjects().forEach(o -> o.setup(scene, this.gameEventsManager, this.inputEventsManager));

        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, this::stop);
        this.gameEventsManager.registerCallback(GameEvent.VICTORY, this::stop);
    }

    /**
     * Get the game events manager.
     * 
     * @return game events manager
     */
    protected final EventsManager<GameEvent> gameEventsManager() {
        return this.gameEventsManager;
    }

    /**
     * Get the input events manager.
     * 
     * @return input events manager
     */
    protected final EventsManager<InputEvent> inputEventsManager() {
        return this.inputEventsManager;
    }

    /**
     * Get the game scene.
     * 
     * @return game scene
     */
    protected final GameScene scene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScoreView score() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final InputEvent event) {
        this.inputEventsManager.scheduleEvent(event);
    }

}
