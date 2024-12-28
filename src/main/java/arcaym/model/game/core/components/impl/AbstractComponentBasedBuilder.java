package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.LinkedList;

import arcaym.model.game.core.components.api.ComponentBasedBuilder;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.impl.AbstractGameObjectBuilder;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link ComponentBasedBuilder}.
 * It provides components storage and usage while leaving the component selection logic.
 */
public abstract class AbstractComponentBasedBuilder 
    extends AbstractGameObjectBuilder 
    implements ComponentBasedBuilder {

    private final Collection<GameComponent> components = new LinkedList<>();

    /**
     * Get current components in builder.
     * 
     * @return components collection
     */
    protected Collection<GameComponent> components() {
        return this.components;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject newInstance(final GameObjectType type, final GameWorld world) {
        final var gameObject = new ComponentBasedGameObject(type, world, this.components());
        components.forEach(component -> component.setObject(gameObject));
        return gameObject;
    }

}
