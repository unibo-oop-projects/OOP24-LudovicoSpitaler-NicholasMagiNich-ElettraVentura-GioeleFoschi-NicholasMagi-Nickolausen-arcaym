package arcaym.controller.game.core.api;

import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for the main game.
 */
public interface Game extends EventsScheduler<InputEvent> {

    /**
     * Start game.
     */
    void start();

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
