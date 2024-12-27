package arcaym.common.point.impl;

import arcaym.common.point.api.Point2D;

/**
 * Basic implementation of {@link Point2D.Factory}.
 */
public class Point2DFactory implements Point2D.Factory {

    private record Point2DRecord(int x, int y) implements Point2D { }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D ofCoordinates(final int x, final int y) {
        return new Point2DRecord(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point2D sum(final Point2D p1, final Point2D p2) {
        return this.ofCoordinates(p1.x() + p2.x(), p1.y() + p2.y());
    }

}
