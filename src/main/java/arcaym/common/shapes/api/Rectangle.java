package arcaym.common.shapes.api;

import arcaym.common.point.api.Point;
import arcaym.common.shapes.impl.BaseRectangle;

/**
 * Interface for a rectangle.
 */
public interface Rectangle extends Shape {

    /**
     * Create rectangle given two vertices.
     * 
     * @param northEast north east vertex
     * @param southWest south west vertex
     * @return resulting rectangle
     */
    static Rectangle of(final Point northEast, final Point southWest) {
        return new BaseRectangle(northEast, southWest);
    }

    /**
     * Get north east vertex.
     * 
     * @return vertex
     */
    Point northEast();

    /**
     * Get north west vertex.
     * 
     * @return vertex
     */
    Point northWest();

    /**
     * Get south east vertex.
     * 
     * @return vertex
     */
    Point southEast();

    /**
     * Get south west vertex.
     * 
     * @return vertex
     */
    Point southWest();

    /**
     * Get base length.
     * 
     * @return base
     */
    int base();

    /**
     * Get height.
     * 
     * @return height
     */
    int height();

}
