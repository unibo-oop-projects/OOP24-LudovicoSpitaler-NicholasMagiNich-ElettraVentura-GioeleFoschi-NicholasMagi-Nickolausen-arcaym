package arcaym.model.game.core.objects.api;

import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;

/**
 * Base interface for representing a {@link GameObject} builder.
 * This interface should be extended by other interfaces to add build steps and
 * not directly implemented by classes.
 */
public interface GameObjectBuilder {

    /**
     * Build game object in the given world and register all events obersers.
     * 
     * @param world game world
     * @param gameEventsScheduler game events manager
     * @param inpuEventsScheduler input events manager
     * @return resulting game object
     */
    GameObject build(
        GameWorld world, 
        EventsScheduler<GameEvent> gameEventsScheduler,
        EventsScheduler<InputEvent> inpuEventsScheduler
    );

}
