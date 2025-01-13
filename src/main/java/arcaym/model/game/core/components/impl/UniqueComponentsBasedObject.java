package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arcaym.controller.game.core.objects.api.GameObjectType;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneView;
import arcaym.model.game.core.components.api.ComponentsBasedObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Implementation of {@link ComponentsBasedObject} that uses a {@link Set} of components.
*/
public class UniqueComponentsBasedObject extends AbstractGameObject implements ComponentsBasedObject {

    private final Set<GameComponent> components = new HashSet<>();

    /**
     * Initialize with the given object type.
     * 
     * @param type game object type
     */
    public UniqueComponentsBasedObject(final GameObjectType type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(
        final GameSceneView scene,
        final EventsSubscriber<GameEvent> gameEventsSubscriber,
        final EventsSubscriber<InputEvent> inputEventsSubscriber
    ) {
        this.components.forEach(c -> c.setup(scene, gameEventsSubscriber, inputEventsSubscriber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(
        final long deltaTime, 
        final EventsScheduler<GameEvent> eventsScheduler, 
        final GameSceneView scene
    ) {
        this.components.forEach(c -> c.update(deltaTime, eventsScheduler, scene));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(final GameComponent component) {
        this.components.add(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeComponent(final GameComponent component) {
        this.components.remove(component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameComponent> getComponents() {
        return Collections.unmodifiableCollection(this.components);
    }

}
