package arcaym.model.game.core.objects.api;

import arcaym.model.game.core.world.api.GameWorld;

/**
 * Interface for a basic game object.
 */
public interface GameObject {

    /**
     * Setup object for world.
     * 
     * @param world game world
     */
    void setup(GameWorld world);

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     */
    void update(long deltaTime);

}
