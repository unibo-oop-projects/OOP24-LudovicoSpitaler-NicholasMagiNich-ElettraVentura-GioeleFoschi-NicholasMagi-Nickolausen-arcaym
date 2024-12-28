package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.common.position.api.Position;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObject}.
 * It provides access to basic fields while leaving the object 
 * interaction with the game.
 */
public abstract class AbstractGameObject implements GameObject {

    /**
     * Factory used for the creation of all {@link Position} instances.
     */
    protected static final Position.Factory POSITION_FACTORY = Position.Factory.newDefault();

    private Position position = POSITION_FACTORY.zero();
    private final GameWorld world;
    private final GameObjectType type;

    /**
     * Initialize game object in the given world.
     * 
     * @param type  game object type
     * @param world game world
     */
    protected AbstractGameObject(final GameObjectType type, final GameWorld world) {
        this.type = Objects.requireNonNull(type);
        this.world = Objects.requireNonNull(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectType type() {
        return this.type;
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
    public Position getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Position position) {
        this.position = Objects.requireNonNull(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final Position distance) {
        this.position = POSITION_FACTORY.sum(this.position, distance);
    }

}
