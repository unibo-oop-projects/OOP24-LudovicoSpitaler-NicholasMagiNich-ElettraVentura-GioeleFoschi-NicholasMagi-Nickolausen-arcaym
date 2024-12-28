package arcaym.model.game.core.objects.api;

import arcaym.model.game.core.world.api.GameWorld;

/**
 * Base interface for representing a {@link GameObject} builder.
 * This interface should be extended by other interfaces to add build steps and
 * not directly implemented by classes.
 */
public interface GameObjectBuilder {

    /**
     * Build game object in the given world.
     * 
     * @param world game world
     * @return resulting game object
     */
    GameObject build(GameWorld world);

}
