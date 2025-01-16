package arcaym.common.geometry.impl;

import java.util.Objects;

/**
 * Basic implementation of a rectangle.
 */
public record Rectangle(Point northEast, Point southWest) {

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
     * {@inheritDoc}
     */
    public Point northWest() {
        return new Point(this.southWest.x(), this.northEast.y());
    }

    /**
     * {@inheritDoc}
     */
    public Point southEast() {
        return Point.of(this.northEast.x(), this.southWest.y());
    }

    /**
     * {@inheritDoc}
     */
    public double base() {
        return this.southWest.subtract(this.northEast).x();
    }

    /**
     * {@inheritDoc}
     */
    public double height() {
        return this.southWest.subtract(this.northEast).y();
    }

}
