package arcaym.controller.game.core.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameObserver;

/**
 * Abstract implementation of {@link GameScene}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameSceneManager implements GameScene {

    private final Set<GameObject> gameObjects = new HashSet<>();
    private final List<CreationEvent> creationEvents = new ArrayList<>();
    private final List<GameObject> destroyEvents = new ArrayList<>();
    private final GameObserver gameObserver;

    private record CreationEvent(GameObjectType type, Point position) { }

    /**
     * Initialize with the given game observer.
     * 
     * @param gameObserver game observer
     */
    protected AbstractGameSceneManager(final GameObserver gameObserver) {
        this.gameObserver = Objects.requireNonNull(gameObserver);
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
        return Collections.unmodifiableCollection(this.gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObject> gameObjects() {
        return Collections.unmodifiableCollection(this.gameObjects);
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
        this.gameObserver.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
        this.gameObserver.destroyObject(gameObject);
    }

}
