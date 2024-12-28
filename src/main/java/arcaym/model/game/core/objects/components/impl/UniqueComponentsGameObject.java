package arcaym.model.game.core.objects.components.impl;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import com.google.common.collect.Sets;

import arcaym.model.game.core.objects.components.api.ComponentBasedGameObject;
import arcaym.model.game.core.objects.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Implementation of {@link ComponentBasedGameObject} with all the components
 * distinct.
 */
public class UniqueComponentsGameObject extends AbstractGameObject implements ComponentBasedGameObject {

    private final Set<GameObjectComponent> components = Sets.newHashSet();

    /**
     * Initialize game object in the given world.
     * 
     * @param world game world
     */
    public UniqueComponentsGameObject(final GameWorld world) {
        super(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        this.components.forEach(component -> component.update(deltaTime));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(final GameObjectComponent component) {
        Objects.requireNonNull(component);
        final var componentClass = component.getClass();
        if (this.components.stream().map(Object::getClass).anyMatch(componentClass::equals)) {
            throw new IllegalArgumentException(new StringBuilder()
                    .append("Component of type ")
                    .append(componentClass)
                    .append(" already present")
                    .toString());
        }

        this.components.add(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeComponent(final GameObjectComponent component) {
        Objects.requireNonNull(component);
        if (!this.components.contains(component)) {
            throw new IllegalArgumentException(new StringBuilder()
                    .append("No component of type ")
                    .append(component.getClass())
                    .append(" found")
                    .toString());
        }

        this.components.remove(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<GameObjectComponent> componentsStream() {
        return Collections.unmodifiableCollection(this.components).stream();
    }

}
