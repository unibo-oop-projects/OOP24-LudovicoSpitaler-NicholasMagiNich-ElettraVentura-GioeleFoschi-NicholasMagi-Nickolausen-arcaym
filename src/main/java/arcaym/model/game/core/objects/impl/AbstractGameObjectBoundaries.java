package arcaym.model.game.core.objects.impl;

import arcaym.common.point.api.Point;
import arcaym.common.point.impl.BasePoint;
import arcaym.common.point.impl.Points;
import arcaym.common.shapes.api.Rectangle;
import arcaym.common.shapes.impl.BaseRectangle;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBoundaries;

public abstract class AbstractGameObjectBoundaries implements GameObjectBoundaries {
    private int radius;
    private GameObject gameObject;

    public AbstractGameObjectBoundaries(Integer size, AbstractGameObject gameObject){
        this.radius = size/2;
        this.gameObject = gameObject;
    }

    @Override
    public boolean isInside(Point point) {
        return Points.distance(point, gameObject.getPosition()) <= radius;
    }

    @Override
    public Rectangle drawArea() {
        var pointOperations = new BasePoint(radius, radius);
        return new BaseRectangle(gameObject.getPosition().subtract(pointOperations), gameObject.getPosition().sum(pointOperations));
    }

}
