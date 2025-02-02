package arcaym.model.game.core.engine.api;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link Game} builder.
 */
public interface GameBuilder {

    /**
     * Add game object to the game.
     * 
     * @param type game object type
     * @param position game object position
     * @return this builder
     */
    GameBuilder addObject(GameObjectType type, Point position);


    /**
     * Build game with added objects.
     * 
     * @param boundaries total level boundaries
     * @return resulting game
     */
    Game build(Rectangle boundaries);

}
