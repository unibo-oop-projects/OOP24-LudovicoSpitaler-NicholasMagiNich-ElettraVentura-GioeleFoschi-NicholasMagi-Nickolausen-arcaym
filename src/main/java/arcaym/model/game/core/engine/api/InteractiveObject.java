package arcaym.model.game.core.engine.api;

import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;

/**
 * Interface for an object that reacts to game updates.
 */
public interface InteractiveObject {

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     */
    void update(long deltaTime);

    /**
     * Register all {@link GameEvent} observers to manager.
     * 
     * @param scheduler events scheduler
     */
    void registerGameObservers(EventsScheduler<GameEvent> scheduler);

    /**
     * Register all {@link InputEvent} observers to manager.
     * 
     * @param scheduler events scheduler
     */
    void registerInputObservers(EventsScheduler<InputEvent> scheduler);

}
