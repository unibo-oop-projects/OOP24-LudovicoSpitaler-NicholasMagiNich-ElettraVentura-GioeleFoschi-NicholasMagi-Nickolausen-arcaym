package arcaym.controller.game.events.api;

/**
 * Interface for a general events manager.
 * 
 * @param <T> events type
 */
public interface EventsManager<T> extends EventsScheduler<T>, EventsSubscriber<T> {

    /**
     * Notify all observers of all pending events.
     */
    void consumePendingEvents();

}
