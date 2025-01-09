package arcaym.model.game.core.objects.impl;

import java.util.Objects;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.events.api.Events.Subscriber;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectBuilder;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.scene.api.GameSceneView;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.objects.GameObjectType;

/**
 * Abstract implementation of {@link GameObjectBuilder}.
 * It provides the build steps while leaving the creation of the instance.
 */
@TypeRepresentation
public abstract class AbstractGameObjectBuilder implements
    GameObjectBuilder,
    GameObjectBuilder.ScoreStep,
    GameObjectBuilder.GameEventsStep,
    GameObjectBuilder.InputEventsStep,
    GameObjectBuilder.BuildStep {

    private Optional<GameScene> scene = Optional.empty();
    private Optional<GameScore> score = Optional.empty();
    private Optional<Events.Subscriber<GameEvent>> gameEventsSubscriber = Optional.empty();
    private Optional<Events.Subscriber<InputEvent>> inputEventsSubscriber = Optional.empty();

    /**
     * Get a new game object instance for the final build step.
     * 
     * @param type game object type
     * @param scene game scene
     * @param score game score
     * @return game object instance
     */
    protected abstract GameObject newInstance(GameObjectType type, GameSceneView scene, GameScore score);

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.ScoreStep useScene(final GameScene scene) {
        this.scene = Optional.of(scene);
        return this;
    }

    /**
     * Get game scene in use if set.
     * 
     * @return game scene
     */
    @FieldRepresentation
    public Optional<GameScene> scene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.GameEventsStep useScore(final GameScore score) {
        this.score = Optional.of(score);
        return this;
    }

    /**
     * Get game scene in use if set.
     * 
     * @return game scene
     */
    @FieldRepresentation
    public Optional<GameScore> score() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.InputEventsStep useGameEventsSubscriber(final Events.Subscriber<GameEvent> eventScheduler) {
        this.gameEventsSubscriber = Optional.of(eventScheduler);
        return this;
    }

    /**
     * Get game events scheduler in use if set.
     * 
     * @return game events scheduler
     */
    @FieldRepresentation
    public Optional<Subscriber<GameEvent>> gameEventsSubscriber() {
        return this.gameEventsSubscriber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectBuilder.BuildStep useInputEventsSubscriber(final Events.Subscriber<InputEvent> eventScheduler) {
        this.inputEventsSubscriber = Optional.of(eventScheduler);
        return this;
    }

    /**
     * Get input events scheduler in use if set.
     * 
     * @return input events scheduler
     */
    @FieldRepresentation
    public Optional<Subscriber<InputEvent>> inputEventsSubscriber() {
        return this.inputEventsSubscriber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject build(final GameObjectType type) {
        final var scene = Optionals.orIllegalState(
            this.scene, 
            "Missing game scene from build steps"
        );
        final var score = Optionals.orIllegalState(
            this.score, 
            "Missing game score from build steps"
        );
        final var gameEventsScheduler = Optionals.orIllegalState(
            this.gameEventsSubscriber, 
            "Missing game events scheduler from build steps"
        );
        final var inputEventsScheduler = Optionals.orIllegalState(
            this.inputEventsSubscriber, 
            "Missing game inputs scheduler from build steps"
        );

        final var gameObject = this.newInstance(Objects.requireNonNull(type), scene, score);
        scene.addObject(gameObject);
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
