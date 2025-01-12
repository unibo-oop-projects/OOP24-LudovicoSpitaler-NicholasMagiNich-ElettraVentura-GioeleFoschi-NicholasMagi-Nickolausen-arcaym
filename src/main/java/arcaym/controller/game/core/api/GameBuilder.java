package arcaym.controller.game.core.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link Game} builder.
 */
public interface GameBuilder {

    /**
     * Add game object to the game.
     * 
     * @param gameObjectType game object type
     * @param position game object position
     * @return this builder
     */
    GameBuilder addObject(GameObjectType gameObjectType, Point position);


    /**
     * Build game with added objects.
     * 
     * @param gameObserver game observer
     * @return resulting game
     */
    Game build(GameObserver gameObserver);

}
