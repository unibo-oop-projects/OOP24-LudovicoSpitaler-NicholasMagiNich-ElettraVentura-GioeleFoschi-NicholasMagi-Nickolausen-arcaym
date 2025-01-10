package arcaym.controller.game.core.events.api;

/**
 * Interface for an events notifier.
 * 
 * @param <T> events type
 */
public interface EventsNotifier<T> {

    /**
     * Notify all observers of all pending events.
     */
    void notifyObservers();

}
