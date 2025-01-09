package arcaym.model.game.core.components.impl;

import java.util.Optional;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Abstract implementation of {@link GameComponent}.
 * It provides game object access while leaving the logic.
 */
@TypeRepresentation
public abstract class AbstractGameComponent implements GameComponent {

    private Optional<GameObject> gameObject;

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public Optional<GameObject> getObject() {
        return this.gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObject(final GameObject object) {
        this.gameObject = Optional.ofNullable(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
