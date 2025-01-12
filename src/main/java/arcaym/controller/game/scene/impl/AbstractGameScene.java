package arcaym.controller.game.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import arcaym.controller.game.core.api.GameObserver;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Abstract implementation of {@link GameScene}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameScene implements GameScene {

    private final Set<GameObject> gameObjects = new HashSet<>();
    private final List<CreationInfo> creationEvents = new ArrayList<>();
    private final List<GameObject> destroyEvents = new ArrayList<>();
    private final GameObserver gameObserver;

    /**
     * Initialize with the given game observer.
     * 
     * @param gameObserver game observer
     */
    protected AbstractGameScene(final GameObserver gameObserver) {
        this.gameObserver = Objects.requireNonNull(gameObserver);
    }

    /**
     * Create a new instance of a game object.
     * 
     * @param gameObjectType game object type
     * @return resulting object
     */
    protected abstract GameObject createInstance(GameObjectType gameObjectType);

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleCreation(final CreationInfo creationInfo) {
        this.creationEvents.add(creationInfo);
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
    public Collection<GameObjectInfo> getGameObjectsInfos() {
        return Collections.unmodifiableCollection(this.gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObject> getGameObjects() {
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

    private void createObject(final CreationInfo creationEvent) {
        final var gameObject = this.createInstance(creationEvent.type());
        gameObject.setPosition(creationEvent.position());
        this.gameObserver.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
        this.gameObserver.destroyObject(gameObject);
    }

}
