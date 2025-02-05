package arcaym.model.game.core.engine.api;

import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.events.impl.InputEvent;

/**
 * Interface for the main game.
 */
public interface Game extends EventsScheduler<InputEvent> {

    /**
     * Start game with given observer.
     * 
     * @param observer game observer
     */
    void start(GameObserver observer);

    /**
     * Schedule ending of the game.
     */
    void scheduleStop();

    /**
     * Get game state.
     * 
     * @return game state
     */
    GameStateInfo state();

}
