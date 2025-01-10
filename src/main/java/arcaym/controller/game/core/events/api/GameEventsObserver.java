package arcaym.controller.game.core.events.api;

import arcaym.model.game.events.api.GameEvent;

/*
Due to generic type ereasure, you need separate observer interfaces for each event type
to be able to implement both at the same time.
*/

/**
 * Interface for a game events observer.
 */
public interface GameEventsObserver {

    /**
     * Register all game events callbacks to subscriber.
     * 
     * @param eventsSubscriber game events subscriber
     */
    void registerGameEventsCallbacks(EventsSubscriber<GameEvent> eventsSubscriber);

}
