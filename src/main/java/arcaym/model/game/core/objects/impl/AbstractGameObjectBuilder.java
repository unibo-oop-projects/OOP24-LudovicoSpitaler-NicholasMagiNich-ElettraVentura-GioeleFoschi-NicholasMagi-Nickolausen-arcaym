package arcaym.model.game.core.objects.impl;

import java.util.Objects;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObject.BuildSteps;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObjectBuilder}.
 * It provides the build step while leaving abstract the middle steps and the 
 * creation of the instance.
 */
public abstract class AbstractGameObjectBuilder implements GameObject.StepBuilder {

    private Optional<GameWorld> world = Optional.empty();
    private Optional<EventsScheduler<GameEvent>> gameEventsScheduler = Optional.empty();
    private Optional<EventsScheduler<InputEvent>> inputEventsScheduler = Optional.empty();

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
    public BuildSteps.Second addWorld(final GameWorld world) {
        this.world = Optional.ofNullable(world);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildSteps.Third addGameEventsScheduler(final EventsScheduler<GameEvent> scheduler) {
        this.gameEventsScheduler = Optional.ofNullable(scheduler);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuildSteps.Fourth addInputEventsScheduler(final EventsScheduler<InputEvent> scheduler) {
        this.inputEventsScheduler = Optional.ofNullable(scheduler);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject build(final GameObjectType type) {
        final var world = Optionals.orIllegalState(
            this.world, 
            "Missing game world from build steps"
        );
        final var gameEventsScheduler = Optionals.orIllegalState(
            this.gameEventsScheduler, 
            "Missing game events scheduler from build steps"
        );
        final var inputEventsScheduler = Optionals.orIllegalState(
            this.inputEventsScheduler, 
            "Missing game inputs scheduler from build steps"
        );

        final var gameObject = this.newInstance(Objects.requireNonNull(type), world);
        world.scene().addObject(gameObject);
        gameObject.registerGameObservers(gameEventsScheduler);
        gameObject.registerInputObservers(inputEventsScheduler);
        return gameObject;
    }

}
