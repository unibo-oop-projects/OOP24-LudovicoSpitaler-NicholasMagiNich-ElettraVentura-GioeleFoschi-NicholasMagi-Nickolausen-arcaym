package arcaym.model.game.core.events;

import java.util.function.Consumer;

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
