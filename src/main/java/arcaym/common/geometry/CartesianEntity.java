package arcaym.common.geometry;

/**
 * Interface for an entity using a 2D cartesian coordinates system.
 * 
 * <p>
 * Example of usage:
 * </p>
 * <pre>
 * // Implement the interface with the class itself as the generic type
 * {@code class Point implements CartesianEntity<Point>} {
 *      ...
 *      {@code @Override}
 *      Point invertX() { // now all generic methods return the class itself
 *          ...
 *      }
 *      ...
 * }
 * </pre>
 * 
 * @param <T> entity type
 */
public interface CartesianEntity<T extends CartesianEntity<T>> {

    /**
     * @return first coordinate
     */
    double x();

    /**
     * @return second coordinate
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
     * @param other other entity
     * @return result
     */
    T sum(T other);

    /**
     * Perform subtraction between two.
     * 
     * @param other other entity
     * @return result
     */
    T subtract(T other);

    /**
     * Perform multiplication between two.
     * 
     * @param other other entity
     * @return result
     */
    T multiply(T other);

}
