package arcaym.common.geometry.impl;

import java.util.Objects;

import arcaym.common.geometry.api.CartesianEntity;

/**
 * Basic implementation of 2D point.
 * 
 * @param x first coordinate
 * @param y second coordinate
 */
public record Point(double x, double y) implements CartesianEntity<Point> {

    /**
     * Create a point with the given coordinates.
     * 
     * @param x first coordinate
     * @param y second coordinate
     * @return resulting point
     */
    public static Point of(final double x, final double y) {
        return new Point(x, y);
    }

    /**
     * Create a point with coordinates (0, 0).
     * 
     * @return resulting point
     */
    public static Point zero() {
        return of(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertX() {
        return of(-this.x, this.y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertY() {
        return of(this.x, -this.y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point sum(final Point point) {
        Objects.requireNonNull(point);
        return of(this.x + point.x(), this.y + point.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point subtract(final Point point) {
        return this.sum(point.invert());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point multiply(final Point other) {
       return of(this.x * other.x, this.y * other.y); 
    }

}
