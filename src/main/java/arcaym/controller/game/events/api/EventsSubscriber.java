package arcaym.controller.game.events.api;

/**
 * Interface for an events subscriber.
 * 
 * @param <T> events type
 */
public interface EventsSubscriber<T> {

    /**
     * Subscribe callback to the happening of an event.
     * 
     * @param event event
     * @param callback event callback
     */
    void registerCallback(T event, Runnable callback);

}
