package arcaym.model.game.core.components;

import java.util.HashSet;
import java.util.Set;

import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.core.objects.AbstractGameObject;
import arcaym.model.game.core.scene.GameSceneInfo;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.events.InputEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Implementation of {@link ComponentsBasedGameObject} that uses a {@link Set}
 * of components.
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
        final GameStateInfo gameState
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
        final GameStateInfo state
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

}
