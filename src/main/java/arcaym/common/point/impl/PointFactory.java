package arcaym.common.point.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;

/**
 * Basic implementation of {@link Point.Factory}.
 */
public class PointFactory implements Point.Factory {

    private record Point2DRecord(int x, int y) implements Point { }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point ofCoordinates(final int x, final int y) {
        return new Point2DRecord(x, y);
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

}
