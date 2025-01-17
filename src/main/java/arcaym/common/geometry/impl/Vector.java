package arcaym.common.geometry.impl;

import arcaym.common.geometry.api.CartesianEntity;

/**
 * Basic implementation of 2D vector.
 * 
 * @param x x axis component
 * @param y y axis component
 */
public record Vector(double x, double y) implements CartesianEntity<Vector> {

    /**
     * 
     * @param x component on X axis
     * @param y component on Y axis
     * @return Vector with said components
     */
    public static Vector of(final double x, final double y) {
        return new Vector(x, y);
    }

    /**
     * Create a null vector.
     * 
     * @return resulting vector
     */
    public static Vector zero() {
        return of(0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector invertX() {
        return Vector.of(-x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector invertY() {
        return Vector.of(x, -y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector sum(Vector other) {
        return Vector.of(this.x + other.x(), this.y + other.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector subtract(Vector other) {
        return Vector.of(this.x - other.x(), this.y - other.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector multiply(Vector other) {
        return Vector.of(this.x * other.x(), this.y * other.y());
    }

}
