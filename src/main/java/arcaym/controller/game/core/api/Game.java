package arcaym.controller.game.core.api;

import arcaym.controller.game.core.events.api.EventsScheduler;
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
     * Force stop the game.
     */
    void abort();

}
