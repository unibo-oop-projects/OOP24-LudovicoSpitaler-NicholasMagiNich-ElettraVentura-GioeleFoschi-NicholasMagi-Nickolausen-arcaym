package arcaym.model.game.logics.impl;

import arcaym.common.point.api.Point;
import arcaym.common.vector.api.Vector;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.logics.api.MovementHandler;

public class BasicMovementHandler implements MovementHandler {
    private final GameObject object;

    public BasicMovementHandler(GameObject object) {
        this.object = object;
    }

    @Override
    public Point nextPosition(Vector velocity, long deltaTime) {
        Point currentPosition = object.getPosition();
        double newX = currentPosition.x() + (velocity.getX() * deltaTime);
        double newY = currentPosition.y() + (velocity.getY() * deltaTime);
        return Point.of((int) Math.round(newX), (int) Math.round(newY));
    }

    @Override
    public void updatePosition(final Point newPosition) {
        this.object.setPosition(newPosition);
    }

}
