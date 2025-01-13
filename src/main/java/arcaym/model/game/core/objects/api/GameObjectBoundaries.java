package arcaym.model.game.core.objects.api;

import java.util.stream.Stream;

import arcaym.common.point.api.Point;
import arcaym.common.shapes.api.Rectangle;
import arcaym.common.shapes.api.Shape;

/**
 * Interface for a {@link GameObject} boundaries.
 */
public interface GameObjectBoundaries extends Shape {

    /**
     * Check if point is inside boundaries.
     * 
     * @param point point
     * @return check result
     */
    boolean isInside(Point point);

    /**
     * Get minimun rectangle that contains the entire object.
     * 
     * @return rectangle
     */
    Rectangle drawArea();

    /**
     * {@inheritDoc}
     */
    @Override
    default Stream<Point> surface() {
        return drawArea().surface().filter(this::isInside);
    }

}
