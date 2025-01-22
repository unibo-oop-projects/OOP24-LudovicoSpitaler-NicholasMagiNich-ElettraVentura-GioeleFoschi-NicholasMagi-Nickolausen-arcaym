package arcaym.model.game.logics.impl;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.logics.api.MovementHandler;

/**
 * Basic implementation of {@MovementHandler}.
 */
public class BasicMovementHandler implements MovementHandler {
    private final GameObject object;

    /**
     * Constructor with gameobject as an argument.
     * 
     * @param object
     */
    public BasicMovementHandler(final GameObject object) {
        this.object = object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point nextPosition(final Vector velocity, final long deltaTime) {
        final Point currentPosition = object.getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        return Point.of(newX, newY);
    }

}
