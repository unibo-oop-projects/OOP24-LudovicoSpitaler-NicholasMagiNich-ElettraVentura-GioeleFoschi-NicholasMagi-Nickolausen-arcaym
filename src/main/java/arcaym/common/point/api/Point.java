package arcaym.common.point.api;

import arcaym.common.point.impl.PointFactory;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;

/**
 * Interface for a 2D point in space.
 */
@TypeRepresentation
public interface Point {

    /**
     * First coordinate.
     * 
     * @return value of the coordinate
     */
    @FieldRepresentation
    int x();

    /**
     * Second coordinate.
     * 
     * @return value of the coordinate
     */
    @FieldRepresentation
    int y();

    /**
     * Interface for a {@link Point} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new PointFactory();
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
        Point difference(Point p1, Point p2);

    }

}
