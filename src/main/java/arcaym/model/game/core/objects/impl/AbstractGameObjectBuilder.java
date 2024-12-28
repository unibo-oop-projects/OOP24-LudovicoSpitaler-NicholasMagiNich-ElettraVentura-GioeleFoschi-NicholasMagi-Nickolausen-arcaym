package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBuilder;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.core.world.events.api.EventsScheduler;
import arcaym.model.game.core.world.events.api.GameEvent;
import arcaym.model.game.core.world.events.api.InputEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObjectBuilder}.
 * It provides the build step while leaving abstract the middle steps and the 
 * creation of the instance.
 */
public abstract class AbstractGameObjectBuilder implements GameObjectBuilder {

    /**
     * Get a new game object instance for the build step.
     * 
     * @param type game object type
     * @param world game world
     * @return the object instance
     */
    protected abstract GameObject newInstance(GameObjectType type, GameWorld world); 

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject build(
        final GameObjectType type,
        final GameWorld world,
        final EventsScheduler<GameEvent> gameEventsScheduler,
        final EventsScheduler<InputEvent> inpuEventsScheduler
    ) {
        final var gameObject = this.newInstance(
            Objects.requireNonNull(type), 
            Objects.requireNonNull(world)
        );
        gameObject.registerGameObservers(gameEventsScheduler);
        gameObject.registerInputObservers(inpuEventsScheduler);
        world.scene().addObject(gameObject);
        return gameObject;
    }

}
