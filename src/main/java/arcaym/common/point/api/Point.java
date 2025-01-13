package arcaym.common.point.api;

import arcaym.common.point.impl.BasePoint;

/**
 * Interface for a 2D point in space.
 */
public interface Point {

    /**
     * Create a point with the given coordinates.
     * 
     * @param x first coordinate
     * @param y second coordinate
     * @return resulting point
     */
    static Point of(final int x, final int y) {
        return new BasePoint(x, y);
    }

    /**
     * Create a point with coordinates (0, 0).
     * 
     * @return resulting point
     */
    static Point zero() {
        return of(0, 0);
    }

    /**
     * First coordinate.
     * 
     * @return value of the coordinate
     */
    int x();

    /**
     * Second coordinate.
     * 
     * @return value of the coordinate
     */
    int y();

    /**
     * Invert first coordinate of the point.
     * 
     * @return resulting point
     */
    Point invertX();

    /**
     * Invert second coordinate of the point.
     * 
     * @return resulting point
     */
    Point invertY();

    /**
     * Invert both coordinates of the point.
     * 
     * @return resulting point
     */
    default Point invert() {
        return this.invertX().invertY();
    }

    /**
     * Sum with another point coordinate-by-coordinate.
     * 
     * @param point other point
     * @return resulting point
     */
    Point sum(Point point);

    /**
     * Subtract another point coordinate-by-coordinate.
     * 
     * @param point other point
     * @return resulting point
     */
    Point subtract(Point point);

}
