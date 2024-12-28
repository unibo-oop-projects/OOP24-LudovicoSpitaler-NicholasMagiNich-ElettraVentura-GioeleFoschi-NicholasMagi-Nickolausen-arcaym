package arcaym.common.point.api;

import arcaym.common.point.impl.PointFactory;

/**
 * Interface for a 2D point in space.
 */
public interface Point {

    /**
     * First coordinate.
     * @return value of the coordinate
     */
    int x();

    /**
     * Second coordinate.
     * @return value of the coordinate
     */
    int y();

    /**
     * Interface for a {@link Point} factory.
     */
    interface Factory {

        /**
         * Get default factory implementation.
         * 
         * @return default factory
         */
        static Factory newDefault() {
            return new PointFactory();
        }

        /**
         * Create point form coordinates.
         * @param x first coordinate
         * @param y second coordinate
         * @return resulting point
         */
        Point ofCoordinates(int x, int y);

        /**
         * Create point with 0,0 coordinates.
         * @return resulting point
         */
        default Point zero() {
            return this.ofCoordinates(0, 0);
        }

        /**
         * Sum two points coordinate-by-coordinate.
         * @param p1 first point
         * @param p2 second point
         * @return resulting point
         */
        Point sum(Point p1, Point p2);

    }

}
