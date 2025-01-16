package arcaym.model.game.logics.api;

import arcaym.common.point.api.Point;
import arcaym.common.vector.api.Vector;

/**
 * Interface for a handler of movement.
 */
public interface MovementHandler {
    /**
     * 
     * @param velocity  of an object
     * @param deltaTime
     * @return the newest position this object should have (it does not change the
     *         position of the object)
     */
    Point nextPosition(Vector velocity, long deltaTime);

    /**
     * changes the position of the object to the position in input
     * 
     * @param newPosition
     */
    void updatePosition(Point newPosition);
}
