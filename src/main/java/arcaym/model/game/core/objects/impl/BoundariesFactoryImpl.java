package arcaym.model.game.core.objects.impl;

import arcaym.common.point.api.Point;
import arcaym.common.point.impl.Points;
import arcaym.common.shapes.api.Rectangle;
import arcaym.model.game.core.objects.api.BoundariesFactory;
import arcaym.model.game.core.objects.api.GameObjectBoundaries;

public class BoundariesFactoryImpl implements BoundariesFactory {
    private final int tileSize;

    public BoundariesFactoryImpl(int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public GameObjectBoundaries squareBoundaries(Point center) {
        return new GameObjectBoundaries() {
            int halfSide = tileSize / 2;

            @Override
            public boolean isInside(Point point) {
                return (Math.abs(point.x()-center.x())<=halfSide) && (Math.abs(point.y()-center.y())<=halfSide);
            }

            @Override
            public Rectangle drawArea() {
                var pointOperations = Point.of(halfSide, halfSide);
                return Rectangle.of(center.subtract(pointOperations),
                        center.sum(pointOperations));
            }

        };
    }

    @Override
    public GameObjectBoundaries circularBoundaries(Point center) {
        return new GameObjectBoundaries() {
            int radius = tileSize / 2;

            @Override
            public boolean isInside(Point point) {

                return Points.distance(point, center) <= radius;
            }

            @Override
            public Rectangle drawArea() {
                var pointOperations = Point.of(radius, radius);
                return Rectangle.of(center.subtract(pointOperations),
                        center.sum(pointOperations));
            }

        };
    }

}
