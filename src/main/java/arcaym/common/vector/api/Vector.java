package arcaym.common.vector.api;

import arcaym.common.vector.impl.BaseVector;

public interface Vector {
    double getX();

    double getY();

    Vector add(Vector other);

    Vector subtract(Vector other);

    Vector invert();

    double module();

    double multiply(Vector other);

    static Vector zero() {
        return new BaseVector(0, 0);
    }

    static Vector of(double x, double y) {
        return new BaseVector(x, y);
    }
}
