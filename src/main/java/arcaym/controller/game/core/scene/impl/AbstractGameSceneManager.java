package arcaym.controller.game.core.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link GameSceneManager}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameSceneManager implements GameSceneManager {

    private record CreationEvent(GameObjectType type, Point position) { }

    private final Set<GameObject> gameObjects = new HashSet<>();
    private List<CreationEvent> creationEvents = new ArrayList<>();
    private List<GameObject> destroyEvents = new ArrayList<>();

    private final GameView view;

    /**
     * Initialize with the given view.
     * 
     * @param view game view
     */
    protected AbstractGameSceneManager(final GameView view) {
        this.view = Objects.requireNonNull(view);
    }

    /**
     * Create a new instance of a game object.
     * 
     * @param type game object type
     * @return resulting object
     */
    protected abstract GameObject createInstance(GameObjectType type);

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleCreation(final GameObjectType type, final Point position) {
        this.creationEvents.add(new CreationEvent(Objects.requireNonNull(type), Objects.requireNonNull(position)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleDestruction(final GameObject gameObject) {
        this.destroyEvents.add(Objects.requireNonNull(gameObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObjectView> getObjects() {
        return Collections.unmodifiableCollection(gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingActions() {
        creationEvents.forEach(this::createObject);
        destroyEvents.forEach(this::destroyObject);
    }

    private void createObject(final CreationEvent event) {
        final var gameObject = this.createInstance(event.type);
        gameObject.setPosition(event.position);
        this.view.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
        this.view.destroyObject(gameObject);
    }

}
