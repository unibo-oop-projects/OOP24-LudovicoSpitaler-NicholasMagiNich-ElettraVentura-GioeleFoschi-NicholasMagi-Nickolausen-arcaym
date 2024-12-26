package arcaym.model.game.core.world.api;

import java.util.function.Consumer;

/**
 * Interface for a generic event manager.
 * 
 * @param <T> events type
 */
public interface EventsManager<T> {

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

    /**
     * Notify all observers of all pending events.
     */
    void notifyObservers();

}
