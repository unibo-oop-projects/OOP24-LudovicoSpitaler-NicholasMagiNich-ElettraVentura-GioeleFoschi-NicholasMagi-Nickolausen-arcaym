package arcaym.common.utils;

/**
 * Basic implementation of a pair of integers.
 * 
 * @param x first value
 * @param y second value
 */
public record Position(int x, int y) {
    /**
     * Create a position with the given coordinates.
     * 
     * @param x first coordinate
     * @param y second coordinate
     * @return resulting position
     */
    public static Position of(final int x, final int y) {
        return new Position(x, y);
    }
}
