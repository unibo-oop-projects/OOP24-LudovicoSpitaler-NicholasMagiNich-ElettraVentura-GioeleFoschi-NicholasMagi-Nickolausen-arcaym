package arcaym.model.game.core.components.impl;

import java.util.Objects;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Abstract implementation of {@link GameComponent}.
 * It provides game object access while leaving the logic.
 */
@TypeRepresentation
public abstract class AbstractGameComponent implements GameComponent {

    private final GameObject gameObject;

    /**
     * Initialize with the given game object.
     * 
     * @param gameObject game object
     */
    protected AbstractGameComponent(final ComponentsBasedGameObject gameObject) {
        this.gameObject = Objects.requireNonNull(gameObject);
    }

    /**
     * Get component game object.
     * 
     * @return game object
     */
    @FieldRepresentation
    protected final GameObject gameObject() {
        return this.gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
