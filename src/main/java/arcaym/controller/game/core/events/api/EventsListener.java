package arcaym.controller.game.core.events.api;

/**
 * Interface for a generic events listener.
 * 
 * @param <T> events type
 */
public interface EventsListener<T> {

    /**
     * Register all events callbacks to subscriber.
     * 
     * @param eventsSubscriber events subscriber
     */
    void registerEventsCallbacks(EventsSubscriber<T> eventsSubscriber);

}
