package arcaym.controller.game.events.api;

import arcaym.model.game.events.api.Event;

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

}
