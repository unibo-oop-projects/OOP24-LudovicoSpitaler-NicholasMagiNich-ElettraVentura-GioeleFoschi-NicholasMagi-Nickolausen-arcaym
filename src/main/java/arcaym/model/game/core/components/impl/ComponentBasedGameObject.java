package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import arcaym.model.game.core.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Implementation of {@link AbstractGameObject} using a collection of {@link GameObjectComponent}.
 */
public class ComponentBasedGameObject extends AbstractGameObject {

    private final Set<GameObjectComponent> components;

    /**
     * Initialize game object in the given world and attach all components to it.
     * 
     * @param type game object type
     * @param world game world
     * @param components components to use
     */
    public ComponentBasedGameObject(
        final GameObjectType type,
        final GameWorld world,
        final Collection<GameObjectComponent> components
    ) {
        super(type, world);
        this.components = Set.copyOf(components);
    }

    /**
     * Initialize game object in the given world without components.
     * 
     * @param type game object type
     * @param world game world
     */
    public ComponentBasedGameObject(final GameObjectType type, final GameWorld world) {
        this(type, world, Collections.emptySet());
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
    public void registerGameObservers(final EventsScheduler<GameEvent> scheduler) {
        this.components.forEach(component -> component.registerGameObservers(scheduler));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerInputObservers(final EventsScheduler<InputEvent> scheduler) {
        this.components.forEach(component -> component.registerInputObservers(scheduler));
    }

}
