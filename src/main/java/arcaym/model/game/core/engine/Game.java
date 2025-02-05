package arcaym.model.game.core.engine;

import arcaym.controller.game.GameObserver;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.events.InputEvent;

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
     * @return game state
     */
    GameStateInfo state();

}
