package arcaym.model.game.core.engine;

import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.events.GameEvent;

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
