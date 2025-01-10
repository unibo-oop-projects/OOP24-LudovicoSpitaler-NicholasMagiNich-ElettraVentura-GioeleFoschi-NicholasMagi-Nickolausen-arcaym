package arcaym.controller.game.core.events.api;

import arcaym.controller.game.core.scene.api.GameScene;
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
     * @param scene game scene
     */
    void registerInputEventsCallbacks(EventsSubscriber<InputEvent> eventsSubscriber, GameScene scene);

}
