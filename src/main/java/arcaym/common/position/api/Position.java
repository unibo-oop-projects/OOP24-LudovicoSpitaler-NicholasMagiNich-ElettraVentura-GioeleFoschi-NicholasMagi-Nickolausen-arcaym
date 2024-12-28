package arcaym.common.position.api;

import arcaym.common.position.impl.PositionFactory;

/**
 * Interface for a 2D point in space.
 */
public interface Position {

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
     * Interface for a {@link Position} factory.
     */
    interface Factory {

        /**
         * Get default factory implementation.
         * 
         * @return default factory
         */
        static Factory newDefault() {
            return new PositionFactory();
        }

        /**
         * Create point form coordinates.
         * @param x first coordinate
         * @param y second coordinate
         * @return resulting point
         */
        Position ofCoordinates(int x, int y);

        /**
         * Create point with 0,0 coordinates.
         * @return resulting point
         */
        default Position zero() {
            return this.ofCoordinates(0, 0);
        }

        /**
         * Sum two points coordinate-by-coordinate.
         * @param p1 first point
         * @param p2 second point
         * @return resulting point
         */
        Position sum(Position p1, Position p2);

    }

}
