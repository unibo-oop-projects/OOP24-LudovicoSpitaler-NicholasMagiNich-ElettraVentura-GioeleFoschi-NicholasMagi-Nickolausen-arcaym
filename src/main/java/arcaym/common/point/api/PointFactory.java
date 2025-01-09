package arcaym.common.point.api;

import arcaym.common.point.impl.BasePointFactory;

/**
 * Interface for a {@link Point} factory.
 */
public interface PointFactory {

    /**
     * Get new instance of the default factory implementation.
     * 
     * @return factory instance
     */
    static PointFactory newDefault() {
        return new BasePointFactory();
    }

    /**
     * Create point form coordinates.
     * 
     * @param x first coordinate
     * @param y second coordinate
     * @return resulting point
     */
    Point ofCoordinates(int x, int y);

    /**
     * Create point with 0,0 coordinates.
     * 
     * @return resulting point
     */
    default Point zero() {
        return this.ofCoordinates(0, 0);
    }

    /**
     * Invert first coordinate of point.
     * 
     * @param point point
     * @return resulting point
     */
    Point invertX(Point point);

    /**
     * Invert second coordinate of point.
     * 
     * @param point point
     * @return resulting point
     */
    Point invertY(Point point);

    /**
     * Invert both coordinates of point.
     * 
     * @param point point
     * @return resulting point
     */
    Point invert(Point point);

    /**
     * Sum two points coordinate-by-coordinate.
     * 
     * @param p1 first point
     * @param p2 second point
     * @return resulting point
     */
    Point sum(Point p1, Point p2);

    /**
     * Subtract first point from second coordinate-by-coordinate.
     * 
     * @param p1 first point
     * @param p2 second point
     * @return resulting point
     */
    Point subtract(Point p1, Point p2);

}
