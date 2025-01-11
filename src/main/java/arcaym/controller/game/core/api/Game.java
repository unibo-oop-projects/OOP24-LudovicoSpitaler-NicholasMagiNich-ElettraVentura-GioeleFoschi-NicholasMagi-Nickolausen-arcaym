package arcaym.controller.game.core.api;

import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.score.api.GameScoreView;

/**
 * Interface for the main game.
 */
public interface Game extends EventsScheduler<InputEvent> {

    /**
     * Get game score.
     * 
     * @return game score
     */
    GameScoreView score();

    /**
     * Start game.
     */
    void start();

    /**
     * Force stop the game.
     */
    void stop();

}
