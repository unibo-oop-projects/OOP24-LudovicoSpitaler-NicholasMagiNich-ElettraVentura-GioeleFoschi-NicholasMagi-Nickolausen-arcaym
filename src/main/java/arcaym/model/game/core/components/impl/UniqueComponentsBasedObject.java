package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.components.api.ComponentsBasedObject;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.impl.AbstractGameObject;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link ComponentsBasedObject} that uses a {@link Set} of components.
*/
public class UniqueComponentsBasedObject extends AbstractGameObject implements ComponentsBasedObject {

    private final Set<GameComponent> gameComponents = new HashSet<>();

    /**
     * Initialize with the given object type.
     * 
     * @param gameObjectType game object type
     */
    public UniqueComponentsBasedObject(final GameObjectType gameObjectType) {
        super(gameObjectType);
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
        this.gameComponents.forEach(c -> c.setup(gameEventsSubscriber, inputEventsSubscriber, gameScene, gameState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(
        final long deltaTime, 
        final EventsScheduler<GameEvent> eventsScheduler, 
        final GameSceneInfo gameScene,
        final GameState gameState
    ) {
        this.gameComponents.forEach(c -> c.update(deltaTime, eventsScheduler, gameScene, gameState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addComponent(final GameComponent gameComponent) {
        this.gameComponents.add(gameComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeComponent(final GameComponent gameComponent) {
        this.gameComponents.remove(gameComponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameComponent> getGameComponents() {
        return Collections.unmodifiableCollection(this.gameComponents);
    }

}
