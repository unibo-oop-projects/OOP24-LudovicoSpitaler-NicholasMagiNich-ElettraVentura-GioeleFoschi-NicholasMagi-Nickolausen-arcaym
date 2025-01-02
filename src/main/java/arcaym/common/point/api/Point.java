package arcaym.common.point.api;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;

/**
 * Interface for a 2D point in space.
 */
@TypeRepresentation
public interface Point {

    /**
     * First coordinate.
     * 
     * @return value of the coordinate
     */
    @FieldRepresentation
    int x();

    /**
     * Second coordinate.
     * 
     * @return value of the coordinate
     */
    @FieldRepresentation
    int y();

}
