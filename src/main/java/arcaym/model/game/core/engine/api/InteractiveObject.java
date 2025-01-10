package arcaym.model.game.core.engine.api;

import arcaym.controller.game.core.events.api.EventsScheduler;
import arcaym.controller.game.core.events.api.GameEventsObserver;
import arcaym.controller.game.core.events.api.InputEventsObserver;
import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;

/**
 * Interface for an object that reacts to game updates.
 */
public interface InteractiveObject extends 
    GameEventsObserver,
    InputEventsObserver {

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     * @param eventsScheduler game events scheduler
     * @param scene game scene
     */
    void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, GameScene scene);

}
