package arcaym.common.geometry.impl;

import java.util.Objects;

/**
 * Basic implementation of a rectangle.
 * 
 * @param northWest north east vertex
 * @param southEast south west vertex
 */
public record Rectangle(Point northWest, Point southEast) {

    /**
     * Create a rectangle with the given vertexes.
     * 
     * @param northWest north east vertex
     * @param southEast south west vertex
     * @return resulting rectangle
     */
    public static Rectangle of(final Point northWest, final Point southEast) {
        return new Rectangle(northWest, southEast);
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
     * @param northWest north east angle
     * @param southEast south west angle
     */
    public Rectangle(final Point northWest, final Point southEast) {
        Objects.requireNonNull(northWest);
        Objects.requireNonNull(southEast);
        if (northWest.x() > southEast.x() || northWest.y() > southEast.y()) {
            throw new IllegalArgumentException(
                new StringBuilder("Points ")
                    .append(northWest)
                    .append(", ")
                    .append(southEast)
                    .append(" are not valid NE/SW angles")
                    .toString()
            );
        }
        this.northWest = northWest;
        this.southEast = southEast;
    }

    /**
     * Get north west angle.
     * 
     * @return angle position
     */
    public Point northEast() {
        return new Point(this.southEast.x(), this.northWest.y());
    }

    /**
     * Get south east angle.
     * 
     * @return angle position
     */
    public Point southWest() {
        return Point.of(this.northWest.x(), this.southEast.y());
    }

    /**
     * Get base.
     * 
     * @return base value
     */
    public double base() {
        return this.southEast.subtract(this.northWest).x();
    }

    /**
     * Get height.
     * 
     * @return height value
     */
    public double height() {
        return this.southEast.subtract(this.northWest).y();
    }

}
