package arcaym.common;

/**
 * Basic 2D point implementation.
 * 
 * @param x x coordinate
 * @param y y coordinate
 */
public record Point(int x, int y) {

    /**
     * Sum point to another coordinate-by-coordinate.
     * 
     * @param other point to sum
     * @return resulting point
     */
    public Point sum(final Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }
}
