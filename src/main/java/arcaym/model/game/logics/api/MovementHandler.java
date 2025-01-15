package arcaym.model.game.logics.api;

import arcaym.common.point.api.Point;
import arcaym.common.vector.api.Vector;

public interface MovementHandler {
    Point nextPosition(Vector velocity, long deltaTime);

    void updatePosition(Point newPosition);
}
