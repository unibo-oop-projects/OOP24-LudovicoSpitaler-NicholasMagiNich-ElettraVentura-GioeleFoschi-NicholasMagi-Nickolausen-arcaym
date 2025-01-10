package arcaym.common.point.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;

/**
 * Implementation of {@link Point} that uses a sandard 2D cartesian system.
 * 
 * @param x first coordinate
 * @param y second coordinate
 */
public record Point2D(int x, int y) implements Point {

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertX() {
        return new Point2D(-x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertY() {
        return new Point2D(x, -y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point sum(final Point point) {
        Objects.requireNonNull(point);
        return new Point2D(x + point.x(), y + point.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point subtract(final Point point) {
        return this.sum(point.invert());
    }

}
