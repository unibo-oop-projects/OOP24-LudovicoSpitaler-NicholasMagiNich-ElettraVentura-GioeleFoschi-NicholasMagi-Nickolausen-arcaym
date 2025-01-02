package arcaym.model.game.core.objects.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import arcaym.common.utils.representation.StringRepresentation;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.api.GameComponentProvider;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBuilder;
import arcaym.model.game.core.objects.api.GameObjectBuilderFactory;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Basic implementation of {@link GameObjectBuilderFactory}.
 */
public class BaseGameObjectBuilderFactory implements GameObjectBuilderFactory {

    private GameObject createComponentsObject(
        final Collection<GameComponent> components,
        final GameObjectType type,
        final GameWorld world
    ) {
        Objects.requireNonNull(components);
        final var gameObject = new AbstractGameObject(Objects.requireNonNull(type), Objects.requireNonNull(world)) {
            @Override
            public void update(final long deltaTime, final Events.Scheduler<GameEvent> eventsScheduler) {
                components.forEach(c -> c.update(deltaTime, eventsScheduler));
            }
            @Override
            public void registerGameEventsCallbacks(final Events.Subscriber<GameEvent> eventsSubscriber) {
                components.forEach(c -> c.registerGameEventsCallbacks(eventsSubscriber));
            }
            @Override
            public void registerInputEventsCallbacks(final Events.Subscriber<InputEvent> eventsSubscriber) {
                components.forEach(c -> c.registerInputEventsCallbacks(eventsSubscriber));
            }
        };
        components.forEach(c -> c.setObject(gameObject));
        return gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder ofComponents(final Collection<GameComponent> components) {
        return new AbstractGameObjectBuilder() {
            @Override
            protected GameObject newInstance(final GameObjectType type, final GameWorld world) {
                return createComponentsObject(components, type, world);
            }
            @Override
            public String toString() {
                return StringRepresentation.ofObject(this, Map.of("components", () -> components));
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder ofComponentsProvider(final GameComponentProvider componentsProvider) {
        Objects.requireNonNull(componentsProvider);
        return new AbstractGameObjectBuilder() {
            @Override
            protected GameObject newInstance(final GameObjectType type, final GameWorld world) {
                return createComponentsObject(componentsProvider.ofType(type), type, world);
            }
            @Override
            public String toString() {
                return StringRepresentation.ofObject(this, Map.of("componentsFactory", () -> componentsProvider));
            }
        };
    }

}
