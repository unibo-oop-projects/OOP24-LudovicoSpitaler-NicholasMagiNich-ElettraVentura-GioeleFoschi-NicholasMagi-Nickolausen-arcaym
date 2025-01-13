package arcaym.controller.game.core.api;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.objects.api.GameObjectType;
import arcaym.model.game.score.api.GameScore;

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
     * Build game with added objects and attach observer to it.
     * 
     * @param gameObserver game observer
     * @param score game score
     * @return resulting game
     */
    Game build(GameObserver gameObserver, GameScore score);

}
