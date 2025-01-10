package arcaym.controller.game.core.events.api;

import java.util.function.Consumer;

/**
 * Interface for an events subscriber.
 * 
 * @param <T> events type
 */
public interface EventsSubscriber<T> {

    /**
     * Subscribe callback to the happening of an event.
     * 
     * @param event event
     * @param callback event consumer
     */
    void registerCallback(T event, Consumer<T> callback);

}
