package arcaym.controller.game.events.api;

import arcaym.model.game.events.api.Event;

/**
 * Interface for an events scheduler.
 * 
 * @param <T> events type
 */
public interface EventsScheduler<T extends Event> {

    /**
     * Register the happening of an event.
     * 
     * @param event event
     */
    void scheduleEvent(T event);

}
