package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsManager;
import arcaym.controller.game.events.impl.ThreadSafeEventsManager;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link Game}.
 * It provides events management while leaving the update logic.
 */
public abstract class AbstractThreadSafeGame implements Game {

    private final EventsManager<GameEvent> gameEventsManager = new ThreadSafeEventsManager<>();
    private final EventsManager<InputEvent> inputEventsManager = new ThreadSafeEventsManager<>();
    private final GameState gameState = new DefaultGameState(this.gameEventsManager);
    private final GameScene gameScene;

    /**
     * Initialize with the given scene.
     * 
     * @param gameScene game scene
     */
    protected AbstractThreadSafeGame(final GameScene gameScene) {
        this.gameScene = Objects.requireNonNull(gameScene);
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
        return this.gameScene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final GameView gameView) {
        gameView.registerEventsCallbacks(this.gameEventsManager);
        this.gameScene.consumePendingActions(gameView);
        this.gameScene.getGameObjects().forEach(
            o -> o.setup(this.gameEventsManager, this.inputEventsManager, gameScene, this.gameState)
        );
        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, e -> this.scheduleStop());
        this.gameEventsManager.registerCallback(GameEvent.VICTORY, e -> this.scheduleStop());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameState state() {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final InputEvent event) {
        this.inputEventsManager.scheduleEvent(event);
    }

}
