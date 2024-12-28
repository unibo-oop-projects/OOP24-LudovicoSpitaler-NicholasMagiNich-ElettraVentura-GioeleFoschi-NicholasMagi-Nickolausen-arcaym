package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Abstract implementation of {@link GameObject}.
 * It provides position manipulation and game world access.
 */
public abstract class AbstractGameObject implements GameObject {

    /**
     * Factory used for the creation of all {@link Point} instances.
     */
    protected static final Point.Factory POINT_FACTORY = Point.Factory.newDefault();

    private Point position = POINT_FACTORY.zero();
    private final GameWorld world;

    /**
     * Initialize game object in the given world.
     * 
     * @param world game world
     */
    protected AbstractGameObject(final GameWorld world) {
        this.world = Objects.requireNonNull(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameWorld world() {
        return this.world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Point getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Point position) {
        this.position = Objects.requireNonNull(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Point distance) {
        this.setPosition(POINT_FACTORY.sum(this.position, distance));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void update(long deltaTime);

}
