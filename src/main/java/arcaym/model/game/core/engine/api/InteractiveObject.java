package arcaym.model.game.core.engine.api;

import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneView;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for an object that reacts to game updates.
 */
public interface InteractiveObject {

    /**
     * Setup object before game.
     * 
     * @param scene game scene
     * @param gameEventsSubscriber game events subscriber
     * @param inputEventsSubscriber input events subscriber
     */
    void setup(
        GameSceneView scene, 
        EventsSubscriber<GameEvent> gameEventsSubscriber, 
        EventsSubscriber<InputEvent> inputEventsSubscriber
    );

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     * @param eventsScheduler game events scheduler
     * @param scene game scene
     */
    void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler, GameSceneView scene);

}
