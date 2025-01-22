package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Abstract implementation of {@link GameObject}.
 * It provides access to basic fields while leaving the object interaction with the game.
 */
@TypeRepresentation
public abstract class AbstractGameObject implements GameObject {

    private final GameObjectType type;
    private final double size;
    private Point position = Point.zero();

    /**
     * Initialize with the given object type.
     * 
     * @param type game object type
     * @param size game object size
     */
    protected AbstractGameObject(final GameObjectType type, final double size) {
        this.type = Objects.requireNonNull(type);
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public GameObjectType type() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
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
    @FieldRepresentation
    public Rectangle boundaries() {
        return Rectangle.centeredSquare(this.size, this.position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
