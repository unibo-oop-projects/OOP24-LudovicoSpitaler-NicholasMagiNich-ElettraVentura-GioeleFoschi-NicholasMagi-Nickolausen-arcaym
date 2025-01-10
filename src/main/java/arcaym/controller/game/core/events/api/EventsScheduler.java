package arcaym.controller.game.core.events.api;

/**
 * Interface for an events scheduler.
 * 
 * @param <T> events type
 */
public interface EventsScheduler<T> {

    /**
     * Register the happening of an event.
     * 
     * @param event event
     */
    void scheduleEvent(T event);

}
