package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Objects;

import com.google.common.collect.Sets;

import arcaym.model.game.core.components.api.ComponentBasedGameObjectBuilder;
import arcaym.model.game.core.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.impl.AbstractGameObjectBuilder;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Implementation of {@link ComponentBasedGameObjectBuilder} that forces all
 * components to be of unique types.
 */
public class UniqueComponentsGameObjectBuilder extends AbstractGameObjectBuilder implements ComponentBasedGameObjectBuilder {

    private final Collection<GameObjectComponent> components = Sets.newHashSet();

    /**
     * @see AbstractGameObjectBuilder#AbstractGameObjectBuilder(GameObjectType)
     * 
     * @param type game object type
     */
    public UniqueComponentsGameObjectBuilder(final GameObjectType type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentBasedGameObjectBuilder addComponent(final GameObjectComponent component) {
        final var componentClass = Objects.requireNonNull(component).getClass();
        if (this.components.stream()
                .map(Object::getClass)
                .anyMatch(componentClass::equals)) {
            throw new IllegalArgumentException(new StringBuilder()
                    .append("Component of type ")
                    .append(componentClass)
                    .append(" already present")
                    .toString());
        }
        this.components.add(component);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject newInstance(final GameWorld world) {
        return new ComponentBasedGameObject(this.type(), world, this.components);
    }

}
