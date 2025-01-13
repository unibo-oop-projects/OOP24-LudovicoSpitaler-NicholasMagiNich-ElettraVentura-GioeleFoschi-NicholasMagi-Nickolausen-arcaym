package arcaym.common.point.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;

/**
 * Basic implementation of {@link Point}.
 * 
 * @param x first coordinate
 * @param y second coordinate
 */
public record BasePoint(int x, int y) implements Point {

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertX() {
        return new BasePoint(-x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertY() {
        return new BasePoint(x, -y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point sum(final Point point) {
        Objects.requireNonNull(point);
        return new BasePoint(x + point.x(), y + point.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point subtract(final Point point) {
        return this.sum(point.invert());
    }

}
