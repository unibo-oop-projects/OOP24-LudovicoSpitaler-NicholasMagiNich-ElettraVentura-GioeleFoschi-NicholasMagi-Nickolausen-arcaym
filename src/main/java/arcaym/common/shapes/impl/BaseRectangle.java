package arcaym.common.shapes.impl;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import arcaym.common.point.api.Point;
import arcaym.common.shapes.api.Rectangle;

/**
 * Basic implementation of {@link Rectangle}.
 * @param northEast
 * @param southWest
 */
public record BaseRectangle(Point northEast, Point southWest) implements Rectangle {

    /**
     * Initialize with given angles.
     * 
     * @param northEast north east angle
     * @param southWest south west angle
     */
    public BaseRectangle(final Point northEast, final Point southWest) {
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
    @Override
    public Point northWest() {
        return Point.of(this.southWest.x(), this.northEast.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point southEast() {
        return Point.of(this.northEast.x(), this.southWest.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int base() {
        return this.southWest.subtract(this.northEast).x();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int height() {
        return this.southWest.subtract(this.northEast).y();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<Point> surface() {
        return IntStream.rangeClosed(this.northEast.x(), this.southWest.x()).boxed()
            .flatMap(
                x -> IntStream.rangeClosed(this.northEast.y(), this.southWest.y())
                    .mapToObj(y -> Point.of(x, y))
            );
    }

}
