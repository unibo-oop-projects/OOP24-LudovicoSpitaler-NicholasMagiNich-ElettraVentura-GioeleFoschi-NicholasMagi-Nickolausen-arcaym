package arcaym.model.game.core.objects;

import java.util.Objects;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Rectangle;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObject}.
 * It provides access to basic fields while leaving the object interaction with
 * the game.
 */
@TypeRepresentation
public abstract class AbstractGameObject implements GameObject {

    private final GameObjectType type;
    private final double size;
    private final int zIndex;
    private Point position = Point.zero();

    /**
     * Initialize with the given parameters.
     * 
     * @param type type
     * @param size size
     * @param zIndex z index
     */
    protected AbstractGameObject(final GameObjectType type, final double size, final int zIndex) {
        this.type = Objects.requireNonNull(type);
        this.size = size;
        this.zIndex = zIndex;
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
    public int zIndex() {
        return this.zIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
