package arcaym.common.geometry.impl;

import java.util.Objects;

/**
 * Basic implementation of a rectangle.
 * 
 * @param northEast north east vertex
 * @param southWest south west vertex
 */
public record Rectangle(Point northEast, Point southWest) {

    /**
     * Create a rectangle with the given vertexes.
     * 
     * @param northEast north east vertex
     * @param southWest south west vertex
     * @return resulting rectangle
     */
    public static Rectangle of(final Point northEast, final Point southWest) {
        return new Rectangle(northEast, southWest);
    }

    /**
     * Create a square centered on a point.
     * 
     * @param side side size
     * @param center center position
     * @return resulting square
     */
    public static Rectangle centeredSquare(final double side, final Point center) {
        Objects.requireNonNull(center);
        final var offset = new Point(side / 2, side / 2);
        return new Rectangle(center.subtract(offset), center.sum(offset));
    }

    /**
     * Initialize with given angles.
     * 
     * @param northEast north east angle
     * @param southWest south west angle
     */
    public Rectangle(final Point northEast, final Point southWest) {
        Objects.requireNonNull(northEast);
        Objects.requireNonNull(southWest);
        if (northEast.x() > southWest.x() || northEast.y() > southWest.y()) {
            throw new IllegalArgumentException(
                new StringBuilder("Points ")
                    .append(northEast)
                    .append(", ")
                    .append(southWest)
                    .append(" are not valid NE/SW angles")
                    .toString()
            );
        }
        this.northEast = northEast;
        this.southWest = southWest;
    }

    /**
     * Get north west angle.
     * 
     * @return angle position
     */
    public Point northWest() {
        return new Point(this.southWest.x(), this.northEast.y());
    }

    /**
     * Get south east angle.
     * 
     * @return angle position
     */
    public Point southEast() {
        return Point.of(this.northEast.x(), this.southWest.y());
    }

    /**
     * Get base.
     * 
     * @return base value
     */
    public double base() {
        return this.southWest.subtract(this.northEast).x();
    }

    /**
     * Get height.
     * 
     * @return height value
     */
    public double height() {
        return this.southWest.subtract(this.northEast).y();
    }

}
