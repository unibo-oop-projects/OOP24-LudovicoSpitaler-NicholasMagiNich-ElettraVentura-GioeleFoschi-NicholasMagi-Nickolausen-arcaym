package arcaym.common.geometry.api;

public interface CartesianEntity<T extends CartesianEntity<T>> {

    /**
     * Get first coordinate.
     * 
     * @return coordinate value
     */
    double x();

    /**
     * Get second coordinate.
     * 
     * @return coordinate value
     */
    double y();

    /**
     * Invert first coordinate.
     * 
     * @return modified copy
     */
    T invertX();

    /**
     * Invert second coordinate.
     * 
     * @return modified copy
     */
    T invertY();

    /**
     * Invert both coordinates.
     * 
     * @return modified copy
     */
    default T invert() {
        return this.invertX().invertY();
    }

    /**
     * Perform sum between two.
     * 
     * @param other other
     * @return modified copy
     */
    T sum(T other);

    /**
     * Perform subtraction between two.
     * 
     * @param other other
     * @return modified copy
     */
    T subtract(T other);

    /**
     * Perform multiplication between two.
     * 
     * @param other other
     * @return modified copy
     */
    T multiply(T other);

}
