package arcaym.model.game.core.objects.api;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;

/**
 * Interface for a {@link GameObject} boundaries.
 */
public interface GameObjectBoundaries {

    /**
     * Check if point is inside boundaries.
     * 
     * @param point point
     * @return check result
     */
    boolean isInside(Point point);

    /**
     * Get minimun rectangle that contains the entire object.
     * 
     * @return rectangle
     */
    Rectangle drawArea();

}
