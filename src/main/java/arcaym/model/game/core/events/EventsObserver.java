package arcaym.model.game.core.events;

import arcaym.model.game.core.engine.GameStateInfo;

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
