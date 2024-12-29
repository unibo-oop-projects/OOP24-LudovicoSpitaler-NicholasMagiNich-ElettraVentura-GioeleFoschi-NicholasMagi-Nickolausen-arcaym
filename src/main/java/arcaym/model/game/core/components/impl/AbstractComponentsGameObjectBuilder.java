package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import arcaym.model.game.core.components.api.ComponentsGameObjectBuilder;
import arcaym.model.game.core.components.api.GameObjectComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.core.objects.impl.AbstractGameObjectBuilder;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link ComponentsGameObjectBuilder}.
 * It provides components storage and usage while leaving the component selection logic.
 */
public abstract class AbstractComponentsGameObjectBuilder 
    extends AbstractGameObjectBuilder 
    implements ComponentsGameObjectBuilder {

    private final Collection<GameObjectComponent> components = new LinkedList<>();

    /**
     * Get current components in builder.
     * 
     * @return components collection
     */
    protected Collection<GameObjectComponent> components() {
        return this.components;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GameObject newInstance(final GameObjectType type, final GameWorld world) {
        final var components = Collections.unmodifiableCollection(this.components);
        final var gameObject = new AbstractGameObject(type, world) {
            @Override
            public void update(final long deltaTime) {
                components.forEach(c -> c.update(deltaTime));
            }
            @Override
            public void registerGameObservers(final EventsScheduler<GameEvent> scheduler) {
                components.forEach(c -> c.registerGameObservers(scheduler));
            }
            @Override
            public void registerInputObservers(final EventsScheduler<InputEvent> scheduler) {
                components.forEach(c -> c.registerInputObservers(scheduler));
            }
        };
        components.forEach(c -> c.setObject(gameObject));
        return gameObject;
    }

}
