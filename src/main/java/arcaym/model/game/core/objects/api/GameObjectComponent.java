package arcaym.model.game.core.objects.api;

import arcaym.model.game.core.world.api.EventsManager;
import arcaym.model.game.core.world.api.GameEvent;
import arcaym.model.game.core.world.api.InputEvent;

/**
 * Interface for basic {@link GameObject} component.
 */
public interface GameObjectComponent {

    /**
     * Register all {@link GameEvent} observers to manager.
     * 
     * @param manager events manager
     */
    void registerGameObservers(EventsManager<GameEvent> manager);

    /**
     * Register all {@link InputEvent} observers to manager.
     * 
     * @param manager events manager
     */
    void registerInputObservers(EventsManager<InputEvent> manager);

    /**
     * Update component for game frame.
     * 
     * @param deltaTime time since last update
     */
    void update(long deltaTime);

}
