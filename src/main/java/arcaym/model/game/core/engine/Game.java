package arcaym.model.game.core.engine;

import arcaym.controller.game.GameUser;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.events.InputEvent;

/**
 * Interface for the main game.
 */
public interface Game extends EventsScheduler<InputEvent> {

    /**
     * Start game with given user.
     * 
     * @param user game user
     */
    void start(GameUser user);

    /**
     * Schedule ending of the game.
     */
    void scheduleStop();

    /**
     * @return game state
     */
    GameStateInfo state();

}
