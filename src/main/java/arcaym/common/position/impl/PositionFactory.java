package arcaym.common.position.impl;

import java.util.Objects;

import arcaym.common.position.api.Position;

/**
 * Basic implementation of {@link Position.Factory}.
 */
public class PositionFactory implements Position.Factory {

    private record PositionRecord(int x, int y) implements Position { }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position ofCoordinates(final int x, final int y) {
        return new PositionRecord(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position sum(final Position p1, final Position p2) {
        Objects.requireNonNull(p1);
        Objects.requireNonNull(p2);
        return this.ofCoordinates(p1.x() + p2.x(), p1.y() + p2.y());
    }

}
