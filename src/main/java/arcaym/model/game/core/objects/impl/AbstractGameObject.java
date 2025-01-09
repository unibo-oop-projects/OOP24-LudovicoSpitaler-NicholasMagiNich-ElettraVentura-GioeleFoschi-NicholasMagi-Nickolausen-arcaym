package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.common.point.api.PointFactory;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.api.GameSceneView;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObject}.
 * It provides access to basic fields while leaving the object interaction with the game.
 */
@TypeRepresentation
public abstract class AbstractGameObject implements GameObject {

    /**
     * Factory used for the creation of all {@link Point} instances.
     */
    protected static final PointFactory POINT_FACTORY = PointFactory.newDefault();

    private Point position = POINT_FACTORY.zero();
    private final GameSceneView scene;
    private final GameScore score;
    private final GameObjectType type;

    /**
     * Initialize game object with access to the given scene and score.
     * 
     * @param type game object type
     * @param scene game scene
     * @param score game score
     */
    protected AbstractGameObject(
        final GameObjectType type,
        final GameSceneView scene,
        final GameScore score
    ) {
        this.type = Objects.requireNonNull(type);
        this.scene = Objects.requireNonNull(scene);
        this.score = Objects.requireNonNull(score);
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
    public GameSceneView scene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScore score() {
        return this.score;
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
    public void move(final Point distance) {
        this.position = POINT_FACTORY.sum(this.position, distance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
