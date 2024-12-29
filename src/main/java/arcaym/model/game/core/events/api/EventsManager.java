package arcaym.model.game.core.events.api;

/**
 * Interface for a generic event manager.
 * 
 * @param <T> events type
 */
public interface EventsManager<T> extends EventsScheduler<T> {

    /**
     * Notify all observers of all pending events.
     */
    void notifyObservers();

}
