package arcaym.controller.game.core.api;

import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.model.game.events.api.InputEvent;
import arcaym.view.game.api.GameView;

/**
 * Interface for the main game.
 */
public interface Game extends EventsScheduler<InputEvent> {

    /**
     * Start game with given view.
     * 
     * @param gameView game view
     */
    void start(GameView gameView);

    /**
     * Schedule ending of the game.
     */
    void scheduleStop();

    /**
     * Get game state.
     * 
     * @return game state
     */
    GameState state();

}
