package arcaym.model.game.core.engine.api;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for an object that reacts to game updates.
 */
public interface InteractiveObject {

    /**
     * Setup object before game.
     * 
     * @param gameEventsSubscriber game events subscriber
     * @param inputEventsSubscriber input events subscriber
     * @param gameScene game scene
     * @param gameState game state
     */
    void setup(
        EventsSubscriber<GameEvent> gameEventsSubscriber, 
        EventsSubscriber<InputEvent> inputEventsSubscriber,
        GameSceneInfo gameScene, 
        GameState gameState
    );

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     * @param eventsScheduler game events scheduler
     * @param gameScene game scene
     * @param gameState game state
     */
    void update(
        long deltaTime, 
        EventsScheduler<GameEvent> eventsScheduler, 
        GameSceneInfo gameScene,
        GameState gameState
    );

}
