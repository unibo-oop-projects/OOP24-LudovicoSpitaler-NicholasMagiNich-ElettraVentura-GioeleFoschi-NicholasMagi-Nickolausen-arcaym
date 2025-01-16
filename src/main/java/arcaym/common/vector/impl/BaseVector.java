package arcaym.common.vector.impl;

import arcaym.common.vector.api.Vector;

/**
 * Basic implementation of {@link Vector}.
 */
public record BaseVector(double x, double y) implements Vector {

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector add(final Vector other) {
        return Vector.of(this.x + other.getX(), this.y + other.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector subtract(final Vector other) {
        return Vector.of(this.x - other.getX(), this.y - other.getY());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector invert() {
        return Vector.of(-this.x, -this.y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double module() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double multiply(final Vector other) {
        return this.x * other.getX() + this.y * other.getY();
    }

}
