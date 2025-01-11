package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.events.api.EventsManager;
import arcaym.controller.game.core.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.score.api.GameScore;
import arcaym.model.game.score.api.GameScoreView;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link Game}.
 * It provides events management while leaving the update logic.
 */
public abstract class AbstractThreadSafeGame implements Game {

    private final EventsManager<GameEvent> gameEventsManager = new ThreadSafeEventsManager<>();
    private final EventsManager<InputEvent> inputEventsManager = new ThreadSafeEventsManager<>();
    private final GameSceneManager scene;
    private final GameScore score;

    /**
     * Initialize with the given scene and view.
     * 
     * @param scene game scene manager
     * @param view game view
     * @param score game score
     */
    protected AbstractThreadSafeGame(
        final GameSceneManager scene, 
        final GameView view,
        final GameScore score
    ) {
        Objects.requireNonNull(view);
        this.scene = Objects.requireNonNull(scene);
        this.score = Objects.requireNonNull(score);

        view.registerEventsCallbacks(this.gameEventsManager);
        score.registerEventsCallbacks(this.gameEventsManager);
        scene.gameObjects().forEach(o -> o.registerGameEventsCallbacks(this.gameEventsManager, scene));
        scene.gameObjects().forEach(o -> o.registerInputEventsCallbacks(this.inputEventsManager, scene));
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
    protected final GameSceneManager scene() {
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
