package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import arcaym.model.game.core.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Implementation of {@link ComponentBasedGameObject} with all the components
 * distinct.
 */
public class ComponentBasedGameObject extends AbstractGameObject {

    private final Set<GameObjectComponent> components;

    /**
     * Initialize game object in the given world and attach all components to it.
     * 
     * @param world game world
     * @param components components to use
     */
    public ComponentBasedGameObject(final GameWorld world, final Collection<GameObjectComponent> components) {
        super(world);
        this.components = Set.copyOf(components);
        this.components.forEach(component -> {
            component.registerGameObservers(world.gameEvents());
            component.registerInputObservers(world.inputEvents());
        });
    }

    /**
     * Initialize game object in the given world without components.
     * 
     * @param world game world
     */
    public ComponentBasedGameObject(final GameWorld world) {
        this(world, Collections.emptySet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        this.components.forEach(component -> component.update(deltaTime));
    }

}
