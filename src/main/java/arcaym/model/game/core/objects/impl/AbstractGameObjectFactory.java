package arcaym.model.game.core.objects.impl;

import java.util.Objects;

import arcaym.controller.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Abstract implementation of {@link GameObjectFactory}.
 * It provides game object after-creation setup while leaving the actual creation. 
 */
public abstract class AbstractGameObjectFactory implements GameObjectFactory {

    private final GameScene scene;
    private final EventsSubscriber<GameEvent> gameEventsSubscriber;
    private final EventsSubscriber<InputEvent> inputEventsSubscriber;

    /**
     * Initialize factory with the objects to use for the game object setup.
     * 
     * @param scene game scene
     * @param gameEventsSubscriber game events subscriber
     * @param inputEventsSubscriber input events subscriber
     */
    protected AbstractGameObjectFactory(
        final GameScene scene,
        final EventsSubscriber<GameEvent> gameEventsSubscriber,
        final EventsSubscriber<InputEvent> inputEventsSubscriber
    ) {
        this.scene = Objects.requireNonNull(scene);
        this.gameEventsSubscriber = Objects.requireNonNull(gameEventsSubscriber);
        this.inputEventsSubscriber = Objects.requireNonNull(inputEventsSubscriber);
    }

    /**
     * Create the game object instance.
     * 
     * @param type game object type
     * @return resulting game object
     */
    protected abstract GameObject createObject(GameObjectType type);

    /**
     * Get the game scene in use.
     * 
     * @return game scene
     */
    protected GameScene scene() {
        return this.scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject ofType(final GameObjectType type) {
        final var gameObject = this.createObject(Objects.requireNonNull(type));
        gameObject.registerGameEventsCallbacks(this.gameEventsSubscriber);
        gameObject.registerInputEventsCallbacks(this.inputEventsSubscriber);
        this.scene.addObject(gameObject);
        return gameObject;
    }

}
