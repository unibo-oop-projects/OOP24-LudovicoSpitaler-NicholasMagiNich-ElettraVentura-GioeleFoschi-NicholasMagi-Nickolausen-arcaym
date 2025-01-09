package arcaym.common.point.impl;

import arcaym.common.point.api.Point;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;

/**
 * Basic implementation of {@link Point}.
 * 
 * @param x first coordinate
 * @param y second coordinate
 */
@TypeRepresentation
public record BasePoint(int x, int y) implements Point {

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public int x() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public int y() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
