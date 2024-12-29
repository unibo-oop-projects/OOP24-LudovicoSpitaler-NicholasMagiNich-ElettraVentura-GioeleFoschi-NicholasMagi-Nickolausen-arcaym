package arcaym.model.game.core.events.api;

import java.util.function.Consumer;

/**
 * Interface for a generic event scheduler.
 * 
 * @param <T> event type
 */
public interface EventsScheduler<T> {
    /**
     * Register callback for a certain event.
     * 
     * @param event    event to observe
     * @param callback operation to perform
     */
    void registerObserver(T event, Consumer<T> callback);

    /**
     * Unregister callback for a certain event.
     * 
     * @param event    observed event
     * @param callback operation to remove
     */
    void unregisterObserver(T event, Consumer<T> callback);

    /**
     * Register the happening of a certain event.
     * 
     * @param event event to notify
     */
    void scheduleEvent(T event);
}
