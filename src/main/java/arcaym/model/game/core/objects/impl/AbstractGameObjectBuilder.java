package arcaym.model.game.core.objects.impl;

import java.util.Objects;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.events.api.Events.Subscriber;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBuilder;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObjectBuilder}.
 * It provides the final build step while leaving the creation of the instance.
 */
public abstract class AbstractGameObjectBuilder implements
    GameObjectBuilder,
    GameObjectBuilder.SecondStep,
    GameObjectBuilder.ThirdStep,
    GameObjectBuilder.FourthStep {

    private Optional<GameWorld> world = Optional.empty();
    private Optional<Events.Subscriber<GameEvent>> gameEventsSubscriber = Optional.empty();
    private Optional<Events.Subscriber<InputEvent>> inputEventsSubscriber = Optional.empty();

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
    public GameObjectBuilder.SecondStep useWorld(final GameWorld world) {
        this.world = Optional.ofNullable(world);
        return this;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Optional<GameWorld> world() {
        return this.world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.ThirdStep useGameEventsSubscriber(final Events.Subscriber<GameEvent> eventScheduler) {
        this.gameEventsSubscriber = Optional.ofNullable(eventScheduler);
        return this;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Optional<Subscriber<GameEvent>> gameEventsSubscriber() {
        return this.gameEventsSubscriber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.FourthStep useInputEventsSubscriber(final Events.Subscriber<InputEvent> eventScheduler) {
        this.inputEventsSubscriber = Optional.ofNullable(eventScheduler);
        return this;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Optional<Subscriber<InputEvent>> inputEventsSubscriber() {
        return this.inputEventsSubscriber;
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
            this.gameEventsSubscriber, 
            "Missing game events scheduler from build steps"
        );
        final var inputEventsScheduler = Optionals.orIllegalState(
            this.inputEventsSubscriber, 
            "Missing game inputs scheduler from build steps"
        );

        final var gameObject = this.newInstance(Objects.requireNonNull(type), world);
        world.scene().addObject(gameObject);
        gameObject.registerGameEventsCallbacks(gameEventsScheduler);
        gameObject.registerInputEventsCallbacks(inputEventsScheduler);
        return gameObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
