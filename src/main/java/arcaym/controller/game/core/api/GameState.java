package arcaym.controller.game.core.api;

import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;

/**
 * Interface for a {@link Game} state.
 */
public interface GameState extends GameStateInfo {

    /**
     * Setup all game events callbacks.
     * 
     * @param eventsSubscriber game events subscriber
     */
    void setupCallbacks(EventsSubscriber<GameEvent> eventsSubscriber);

}
