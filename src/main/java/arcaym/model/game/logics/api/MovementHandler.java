package arcaym.model.game.logics.api;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;

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

}
