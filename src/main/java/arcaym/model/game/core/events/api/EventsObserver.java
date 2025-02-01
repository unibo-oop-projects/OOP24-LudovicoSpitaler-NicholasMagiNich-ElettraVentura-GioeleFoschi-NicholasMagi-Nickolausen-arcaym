package arcaym.model.game.core.events.api;

import arcaym.model.game.core.engine.api.GameStateInfo;

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
