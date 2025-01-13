package arcaym.common.shapes.api;

import java.util.stream.Stream;

import arcaym.common.point.api.Point;

/**
 * Interface for a generic shape.
 */
public interface Shape {

    /**
     * Get a stream of all the points covered.
     * 
     * @return points stream
     */
    Stream<Point> surface();

}
