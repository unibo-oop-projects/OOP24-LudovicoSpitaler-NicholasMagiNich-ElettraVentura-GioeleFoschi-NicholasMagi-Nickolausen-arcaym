package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Interface for a basic game object.
 */
public interface GameObject {

    /**
     * Get world associated with the object.
     * @return the game world
     */
    GameWorld world();

    /**
     * Get object position.
     * 
     * @return position
     */
    Point getPosition();

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Point position);

    /**
     * Move object from current position by distance.
     * 
     * @param distance amount to move on each coordinate
     */
    void move(Point distance);

    /**
     * Update object for new game frame.
     * 
     * @param deltaTime time since last update
     */
    void update(long deltaTime);

}
