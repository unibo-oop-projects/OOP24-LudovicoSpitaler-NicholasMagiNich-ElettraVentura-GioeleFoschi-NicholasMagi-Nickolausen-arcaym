package arcaym.model.game.core.engine.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.engine.api.GameEngine;
import arcaym.model.game.core.engine.api.GameEngineBuilder;
import arcaym.model.game.core.engine.api.GameView;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Abstract implementation of {@link GameEngineBuilder}.
 * It provides the build steps while leaving the creation of the instance.
 */
@TypeRepresentation
public abstract class AbstractGameEngineBuilder implements 
    GameEngineBuilder,
    GameEngineBuilder.SecondStep,
    GameEngineBuilder.ThirdStep {

    private Optional<Set<GameObject>> gameObjects = Optional.empty();
    private Optional<GameView> gameView = Optional.empty();

    /**
     * Get a new game engine instance for the final build step.
     * 
     * @param gameObjects game objects
     * @param gameView game view
     * @return game engine instance
     */
    protected abstract GameEngine newInstance(Set<GameObject> gameObjects, GameView gameView);

    /**
     * {@inheritDoc}
     */
    @Override
    public SecondStep useGameObjects(final Set<GameObject> gameObjects) {
        this.gameObjects = Optional.of(gameObjects);
        return this;
    }

    /**
     * Get game objects in use if set.
     * 
     * @return game objects
     */
    @FieldRepresentation
    public Optional<Set<GameObject>> gameObjects() {
        return this.gameObjects.map(Collections::unmodifiableSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ThirdStep useGameView(final GameView gameView) {
        this.gameView = Optional.of(gameView);
        return this;
    }

    /**
     * Get game view in use if set.
     * 
     * @return game view
     */
    @FieldRepresentation
    public Optional<GameView> gameView() {
        return this.gameView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEngine build() {
        final var gameObjects = Optionals.orIllegalState(
            this.gameObjects, 
            "Missing game objects from build steps"
        );
        final var gameView = Optionals.orIllegalState(
            this.gameView, 
            "Missing game view from build steps"
        );

        return this.newInstance(gameObjects, gameView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
