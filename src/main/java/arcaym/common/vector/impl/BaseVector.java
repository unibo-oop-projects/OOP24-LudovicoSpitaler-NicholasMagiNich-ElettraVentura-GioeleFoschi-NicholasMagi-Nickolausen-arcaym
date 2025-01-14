package arcaym.common.vector.impl;

import arcaym.common.vector.api.Vector;

public class BaseVector implements Vector {
    private final double x;
    private final double y;

    public BaseVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public Vector add(Vector other) {
        return Vector.of(this.x + other.getX(), this.y + other.getY());
    }

    @Override
    public Vector subtract(Vector other) {
        return Vector.of(this.x - other.getX(), this.y - other.getY());
    }

    @Override
    public Vector invert() {
        return Vector.of(-this.x, -this.y);
    }

    @Override
    public double module() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public double multiply(Vector other) {
        return this.x * other.getX()+ this.y * other.getY();
    }

}
