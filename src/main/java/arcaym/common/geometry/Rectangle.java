package arcaym.common.geometry;

import java.util.Objects;

/**
 * Basic implementation of a rectangle.
 * 
 * @param northWest north west vertex
 * @param southEast south east vertex
 */
public record Rectangle(Point northWest, Point southEast) {

    /**
     * Create a rectangle with the given vertexes.
     * 
     * @param northWest north west vertex
     * @param southEast south east vertex
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
     * @param northWest north west angle
     * @param southEast south east angle
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
     * @return north east angle
     */
    public Point northEast() {
        return new Point(this.southEast.x(), this.northWest.y());
    }

    /**
     * @return south west angle
     */
    public Point southWest() {
        return Point.of(this.northWest.x(), this.southEast.y());
    }

    /**
     * @return base length
     */
    public double base() {
        return this.southEast.subtract(this.northWest).x();
    }

    /**
     * @return height value
     */
    public double height() {
        return this.southEast.subtract(this.northWest).y();
    }

    /**
     * @param rect other rectangle
     * @return if this rectangle contains the other
     */
    public boolean contains(final Rectangle rect) {
        return rect.northWest().x() >= this.northWest().x() && rect.northWest().y() >= this.northWest().y()
            && rect.southEast().x() <= this.southEast().x() && rect.southEast().y() <= this.southEast().y();
    }

    /**
     * @param rect other rectangle
     * @return if this rectangle is intersecting with the other
     */
    public boolean intersecting(final Rectangle rect) {
        return this.northWest().x() < rect.northEast().x()
                && this.northEast().x() > rect.northWest().x()
                && this.northWest().y() < rect.southWest().y()
                && this.southWest().y() > rect.northWest().y();
    }

}
