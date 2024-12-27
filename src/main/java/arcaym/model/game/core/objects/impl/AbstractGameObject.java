package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.world.api.GameWorld;

public abstract class AbstractGameObject implements GameObject {

    protected static final Point.Factory POINT_FACTORY = Point.Factory.newDefault();
    protected Point position = POINT_FACTORY.zero();
    protected final GameWorld world;

    protected AbstractGameObject(final GameWorld world) {
        this.world = Objects.requireNonNull(world);
    }
    
    @Override
    public GameWorld world() {
        return this.world;
    }

    @Override
    public Point getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(final Point position) {
        this.position = Objects.requireNonNull(position);        
    }

    @Override
    public void move(final Point distance) {
        this.setPosition(POINT_FACTORY.sum(this.position, distance));
    }

    @Override
    public abstract void update(long deltaTime);
    
}
