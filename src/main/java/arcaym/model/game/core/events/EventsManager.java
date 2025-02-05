package arcaym.model.game.core.events;

/**
 * Interface for a general events manager.
 * 
 * @param <T> events type
 */
public interface EventsManager<T extends Event> extends EventsScheduler<T>, EventsSubscriber<T> {

    /**
     * Notify all observers of all pending events.
     */
    void consumePendingEvents();

    /**
     * Start registering the events.
     */
    void enable();

    /**
     * Stop registering the events.
     */
    void disable();

    /**
     * @return if the events manager is enabled
     */
    boolean isEnabled();

}
