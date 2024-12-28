package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Implementation of {@link AbstractGameObject} using a collection of {@link GameComponent}.
 */
public class ComponentBasedGameObject extends AbstractGameObject {

    private final Collection<GameComponent> components;

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
        final Collection<GameComponent> components
    ) {
        super(type, world);
        this.components = Collections.unmodifiableCollection(Objects.requireNonNull(components));
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
