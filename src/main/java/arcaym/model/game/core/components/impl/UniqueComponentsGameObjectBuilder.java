package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Objects;

import com.google.common.collect.Sets;

import arcaym.model.game.core.components.api.ComponentBasedGameObjectBuilder;
import arcaym.model.game.core.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Implementation of {@link ComponentBasedGameObjectBuilder} that forces all
 * components to be of unique types.
 */
public class UniqueComponentsGameObjectBuilder implements ComponentBasedGameObjectBuilder {

    private final GameWorld world;
    private final Collection<GameObjectComponent> components = Sets.newHashSet();

    /**
     * Initialize builder and set world to attach to the built object.
     * 
     * @param world game world
     */
    public UniqueComponentsGameObjectBuilder(final GameWorld world) {
        this.world = world;
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
    public GameObject build() {
        final var gameObject = new ComponentBasedGameObject(this.world, this.components);
        this.world.scene().addObject(gameObject);
        return gameObject;
    }

}
