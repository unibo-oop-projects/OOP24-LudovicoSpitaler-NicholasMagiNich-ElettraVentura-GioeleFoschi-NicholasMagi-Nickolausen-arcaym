package arcaym.model.game.core.engine.api;

import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;

/**
 * Interface for an object that reacts to game updates.
 */
public interface InteractiveObject extends 
    Events.GameEventObserver,
    Events.InputEventObserver {

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     * @param eventsScheduler time since last update
     */
    void update(long deltaTime, Events.Scheduler<GameEvent> eventsScheduler);

}
