package arcaym.model.game.core.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.controller.game.api.GameObserver;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Abstract implementation of {@link GameScene}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameScene implements GameScene {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGameScene.class);

    private final Map<GameObject, Integer> elements = new HashMap<>();
    private final List<CreationInfo> creationEvents = new ArrayList<>();
    private final List<GameObject> destroyEvents = new ArrayList<>();

    /**
     * Create a new instance of a game object.
     * 
     * @param type game object type
     * @return resulting object
     */
    protected abstract GameObject createObject(GameObjectType type);

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleCreation(final CreationInfo creation) {
        this.creationEvents.add(Objects.requireNonNull(creation));
        LOGGER.info(new StringBuilder("Scheduled creation ").append(creation).toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleDestruction(final GameObject gameObject) {
        this.destroyEvents.add(Objects.requireNonNull(gameObject));
        LOGGER.info(new StringBuilder("Scheduled destruction of ").append(gameObject).toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<? extends GameObjectInfo> getGameObjectsInfos() {
        return this.getGameObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObject> getGameObjects() {
        return this.elements.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingActions(final GameObserver observer) {
        while (!this.creationEvents.isEmpty()) {
            this.createObject(this.creationEvents.removeFirst(), observer);
        }
        while (!this.destroyEvents.isEmpty()) {
            this.destroyObject(this.destroyEvents.removeFirst(), observer);
        }
        LOGGER.info("Finished consuming all pending events");
    }

    private void createObject(final CreationInfo creation, final GameObserver observer) {
        final var gameObject = this.createObject(creation.type());
        gameObject.setPosition(creation.position());
        LOGGER.info(new StringBuilder("Created object ").append(gameObject).toString());
        this.elements.put(gameObject, creation.zIndex());
        observer.createObject(gameObject, creation.zIndex());
    }

    private void destroyObject(final GameObject gameObject, final GameObserver observer) {
        this.elements.remove(gameObject);
        LOGGER.info(new StringBuilder("Destroyed object ").append(gameObject).toString());
        observer.destroyObject(gameObject);
    }

}
