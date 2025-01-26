package arcaym.controller.game.events.api;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.model.game.events.api.Event;

/**
 * Interface for a generic events observer.
 * 
 * @param <T> events type
 */
public interface EventsObserver<T extends Event> {

    /**
     * Register all events callbacks to subscriber.
     * 
     * @param eventsSubscriber events subscriber
     * @param gameState game state
     */
    void registerEventsCallbacks(EventsSubscriber<T> eventsSubscriber, GameStateInfo gameState);

}
