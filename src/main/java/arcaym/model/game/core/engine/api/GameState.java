package arcaym.model.game.core.engine.api;

import arcaym.model.game.core.events.api.EventsSubscriber;
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
