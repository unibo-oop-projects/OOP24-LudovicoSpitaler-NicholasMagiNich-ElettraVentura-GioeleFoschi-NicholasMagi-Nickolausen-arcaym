package arcaym.common.point.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.common.utils.representation.StringRepresentation;

/**
 * Basic implementation of {@link Position.Factory}.
 */
public class PointFactory implements Point.Factory {

    private record PositionRecord(int x, int y) implements Point {
        @Override
        public String toString() {
            return StringRepresentation.ofObject(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point ofCoordinates(final int x, final int y) {
        return new PositionRecord(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertX(final Point point) {
        Objects.requireNonNull(point);
        return this.ofCoordinates(-point.x(), point.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invertY(final Point point) {
        Objects.requireNonNull(point);
        return this.ofCoordinates(point.x(), -point.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point invert(final Point point) {
        return this.invertX(this.invertY(point));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point sum(final Point p1, final Point p2) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        return this.ofCoordinates(p1.x() + p2.x(), p1.y() + p2.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point difference(final Point p1, final Point p2) {
        return this.sum(p1, this.invert(p2));
    }

}
