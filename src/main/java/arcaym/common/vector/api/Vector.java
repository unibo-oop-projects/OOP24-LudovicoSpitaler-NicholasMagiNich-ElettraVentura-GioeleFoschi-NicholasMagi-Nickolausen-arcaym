package arcaym.common.vector.api;

import arcaym.common.vector.impl.BaseVector;

/**
 * Interface for a 2D Vector.
 */
public interface Vector {
    /**
     * 
     * @return component on X axis
     */
    double getX();

    /**
     * 
     * @return component on Y axis
     */
    double getY();

    /**
     * 
     * @param other vector to add
     * @return sum of the two
     */
    Vector add(Vector other);

    /**
     * 
     * @param other vector to subtract
     * @return subtraction of the two
     */
    Vector subtract(Vector other);

    /**
     * 
     * @return this vector with opposite direction
     */
    Vector invert();

    /**
     * 
     * @return module of vector
     */
    double module();

    /**
     * 
     * @param other vector to multiply
     * @return multiplication
     */
    double multiply(Vector other);

    /**
     * 
     * @return null vector
     */
    static Vector zero() {
        return Vector.of(0, 0);
    }

    /**
     * 
     * @param x component on X axis
     * @param y component on Y axis
     * @return Vector with said components
     */
    static Vector of(double x, double y) {
        return new BaseVector(x, y);
    }
}
