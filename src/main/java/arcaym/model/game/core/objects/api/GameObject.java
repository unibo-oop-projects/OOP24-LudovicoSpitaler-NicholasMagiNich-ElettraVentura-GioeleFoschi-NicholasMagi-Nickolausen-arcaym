package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point2D;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Interface for a basic game object.
 */
public interface GameObject {

    /**
     * Get object position.
     * 
     * @return position
     */
    Point2D getPosition();

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Point2D position);

    /**
     * Move object from current position by distance.
     * 
     * @param distance amount to move on each coordinate
     */
    void move(Point2D distance);

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
