package arcaym.common.position.api;

import arcaym.common.position.impl.PositionFactory;

/**
 * Interface for a position in space.
 */
public interface Position {

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
     * Interface for a {@link Position} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new PositionFactory();
        }

        /**
         * Create position form coordinates.
         * 
         * @param x first coordinate
         * @param y second coordinate
         * @return resulting position
         */
        Position ofCoordinates(int x, int y);

        /**
         * Create position with 0,0 coordinates.
         * 
         * @return resulting position
         */
        default Position zero() {
            return this.ofCoordinates(0, 0);
        }

        /**
         * Sum two positions coordinate-by-coordinate.
         * 
         * @param p1 first position
         * @param p2 second position
         * @return resulting position
         */
        Position sum(Position p1, Position p2);

    }

}
