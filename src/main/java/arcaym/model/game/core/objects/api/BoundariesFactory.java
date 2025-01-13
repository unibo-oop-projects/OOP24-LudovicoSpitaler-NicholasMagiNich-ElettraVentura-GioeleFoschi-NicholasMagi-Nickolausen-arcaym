package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;

public interface BoundariesFactory {
    public GameObjectBoundaries circularBoundaries(Point center);
    public GameObjectBoundaries squareBoundaries(Point center);
}
