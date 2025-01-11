package arcaym.controller.game.events.api;

/**
 * Interface for a generic events observer.
 * 
 * @param <T> events type
 */
public interface EventsObserver<T> {

    /**
     * Register all events callbacks to subscriber.
     * 
     * @param eventsSubscriber events subscriber
     */
    void registerEventsCallbacks(EventsSubscriber<T> eventsSubscriber);

}
