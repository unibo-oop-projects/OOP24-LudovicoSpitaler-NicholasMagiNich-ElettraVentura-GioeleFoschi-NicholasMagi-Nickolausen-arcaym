package arcaym.model.game.core.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link GameScene}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameScene implements GameScene {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGameScene.class);

    private final Set<GameObject> gameObjects = new HashSet<>();
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
    public void consumePendingActions(final GameView gameView) {
        this.creationEvents.forEach(c -> this.createObject(c, gameView));
        this.destroyEvents.forEach(d -> this.destroyObject(d, gameView));
        LOGGER.info("Finished consuming all pending events");
    }

    private void createObject(final CreationInfo creation, final GameView gameView) {
        final var gameObject = this.createObject(creation.type());
        gameObject.setPosition(creation.position());
        LOGGER.info(new StringBuilder("Created object ").append(gameObject).toString());
        gameView.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject, final GameView gameView) {
        this.gameObjects.remove(gameObject);
        LOGGER.info(new StringBuilder("Destroyed object ").append(gameObject).toString());
        gameView.destroyObject(gameObject);
    }

}
