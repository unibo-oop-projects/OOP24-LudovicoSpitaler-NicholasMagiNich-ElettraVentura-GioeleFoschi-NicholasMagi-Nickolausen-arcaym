package arcaym.model.game.core.objects.impl;

import arcaym.common.point.api.Point;
import arcaym.common.point.impl.BasePoint;
import arcaym.common.point.impl.Points;
import arcaym.common.shapes.api.Rectangle;
import arcaym.common.shapes.impl.BaseRectangle;
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
            int radius = tileSize / 2;

            @Override
            public boolean isInside(Point point) {

                return Points.distance(point, center) <= radius;
            }

            @Override
            public Rectangle drawArea() {
                var pointOperations = new BasePoint(radius, radius);
                return new BaseRectangle(center.subtract(pointOperations),
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
                var pointOperations = new BasePoint(radius, radius);
                return new BaseRectangle(center.subtract(pointOperations),
                        center.sum(pointOperations));
            }

        };
    }

}
