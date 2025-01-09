package arcaym.model.game.core.objects.impl;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import arcaym.common.utils.representation.StringRepresentation;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.api.GameComponentProvider;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBuilder;
import arcaym.model.game.core.objects.api.GameObjectBuilderFactory;
import arcaym.model.game.core.scene.api.GameSceneView;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.objects.GameObjectType;

/**
 * Basic implementation of {@link GameObjectBuilderFactory}.
 */
public class BaseGameObjectBuilderFactory implements GameObjectBuilderFactory {

    private GameObject createComponentsObject(
        final Set<GameComponent> components,
        final GameObjectType type,
        final GameSceneView scene,
        final GameScore score
    ) {
        Objects.requireNonNull(components);
        final var gameObject = new AbstractGameObject(type, scene, score) {
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
    public GameObjectBuilder ofComponents(final Set<GameComponent> components) {
        return new AbstractGameObjectBuilder() {
            @Override
            protected GameObject newInstance(final GameObjectType type, final GameSceneView scene, final GameScore score) {
                return createComponentsObject(components, type, scene, score);
            }
            @Override
            public String toString() {
                return StringRepresentation.ofObject(this, Map.of("components", () -> Collections.unmodifiableSet(components)));
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
            protected GameObject newInstance(final GameObjectType type, final GameSceneView scene, final GameScore score) {
                return createComponentsObject(componentsProvider.ofType(type), type, scene, score);
            }
            @Override
            public String toString() {
                return StringRepresentation.ofObject(this, Map.of("componentsProvider", () -> componentsProvider));
            }
        };
    }

}
