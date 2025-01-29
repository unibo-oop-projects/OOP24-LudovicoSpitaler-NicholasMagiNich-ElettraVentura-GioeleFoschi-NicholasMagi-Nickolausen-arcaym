package arcaym.controller.game.events.api;

import java.util.function.Consumer;

import arcaym.model.game.events.api.Event;

/**
 * Interface for an events subscriber.
 * 
 * @param <T> events type
 */
public interface EventsSubscriber<T extends Event> {

    /**
     * Subscribe callback to the happening of an event.
     * 
     * @param event event
     * @param callback event callback
     */
    void registerCallback(T event, Consumer<T> callback);

}
