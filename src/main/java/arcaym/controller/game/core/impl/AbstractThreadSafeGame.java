package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsManager;
import arcaym.controller.game.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Abstract implementation of {@link Game}.
 * It provides events management while leaving the update logic.
 */
public abstract class AbstractThreadSafeGame implements Game {

    private final EventsManager<GameEvent> gameEventsManager = new ThreadSafeEventsManager<>();
    private final EventsManager<InputEvent> inputEventsManager = new ThreadSafeEventsManager<>();
    private final GameState state = new DefaultGameState(this.gameEventsManager);
    private final GameScene scene;

    /**
     * Initialize with the given scene and game observer.
     * 
     * @param scene game scene manager
     * @param gameObserver game observer
     */
    protected AbstractThreadSafeGame(final GameScene scene, final GameObserver gameObserver) {
        this.scene = Objects.requireNonNull(scene);
        Objects.requireNonNull(gameObserver).registerEventsCallbacks(this.gameEventsManager);
        scene.consumePendingActions();
        scene.gameObjects().forEach(o -> o.setup(scene, this.gameEventsManager, this.inputEventsManager, this.state));

        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, this::scheduleStop);
        this.gameEventsManager.registerCallback(GameEvent.VICTORY, this::scheduleStop);
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
    public final GameState state() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final InputEvent event) {
        this.inputEventsManager.scheduleEvent(event);
    }

}
