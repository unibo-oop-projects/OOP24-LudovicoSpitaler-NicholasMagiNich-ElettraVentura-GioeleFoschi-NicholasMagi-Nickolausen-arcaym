package arcaym.model.game.logics.impl;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.logics.api.MovementHandler;

/**
 * Basic implementation of {@MovementHandler}
 */
public class BasicMovementHandler implements MovementHandler {
    private final GameObject object;

    public BasicMovementHandler(GameObject object) {
        this.object = object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point nextPosition(Vector velocity, long deltaTime) {
        Point currentPosition = object.getPosition();
        double newX = currentPosition.x() + (velocity.x() * deltaTime);
        double newY = currentPosition.y() + (velocity.y() * deltaTime);
        return Point.of(newX, newY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition(final Point newPosition) {
        this.object.setPosition(newPosition);
    }

}
