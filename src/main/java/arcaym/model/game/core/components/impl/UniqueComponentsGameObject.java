package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link ComponentsBasedGameObject} that uses a {@link Set} of components.
*/
public class UniqueComponentsGameObject extends AbstractGameObject implements ComponentsBasedGameObject {

    private final Set<GameComponent> components = new HashSet<>();

    /**
     * Initialize with the given object type.
     * 
     * @param type game object type
     * @param size game object size
     */
    public UniqueComponentsGameObject(final GameObjectType type, final double size) {
        super(type, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(
        final EventsSubscriber<GameEvent> gameEventsSubscriber,
        final EventsSubscriber<InputEvent> inputEventsSubscriber,
        final GameSceneInfo gameScene,
        final GameState gameState
    ) {
        this.components.forEach(c -> c.setup(gameEventsSubscriber, inputEventsSubscriber, gameScene, gameState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(
        final long deltaTime, 
        final EventsScheduler<GameEvent> eventsScheduler, 
        final GameSceneInfo scene,
        final GameState state
    ) {
        this.components.forEach(c -> c.update(deltaTime, eventsScheduler, scene, state));
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
