package arcaym.controller.game.events.api;

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
     */
    void registerEventsCallbacks(EventsSubscriber<T> eventsSubscriber);

}
