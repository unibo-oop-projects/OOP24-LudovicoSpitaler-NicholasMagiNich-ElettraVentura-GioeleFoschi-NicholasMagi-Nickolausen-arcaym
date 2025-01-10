package arcaym.controller.game.core.events.api;

import arcaym.model.game.events.api.InputEvent;

/*
Due to generic type ereasure, you need separate observer interfaces for each event type
to be able to implement both at the same time.
*/

/**
 * Interface for a input events observer.
 */
public interface InputEventsObserver {

    /**
     * Register all input events callbacks to subscriber.
     * 
     * @param eventsSubscriber input events subscriber
     */
    void registerInputEventsCallbacks(EventsSubscriber<InputEvent> eventsSubscriber);

}
