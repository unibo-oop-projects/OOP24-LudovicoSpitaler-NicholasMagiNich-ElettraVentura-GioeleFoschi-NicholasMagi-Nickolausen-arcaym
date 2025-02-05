package arcaym.model.game.core.engine;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Rectangle;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link Game} builder.
 */
public interface GameBuilder {

    /**
     * Add game object to the game.
     * 
     * @param type game object type
     * @param position game object position
     * @param zIndex z index
     * @return this builder
     */
    GameBuilder addObject(GameObjectType type, Point position, int zIndex);


    /**
     * Build game with added objects.
     * 
     * @param boundaries total level boundaries
     * @return resulting game
     */
    Game build(Rectangle boundaries);

}
