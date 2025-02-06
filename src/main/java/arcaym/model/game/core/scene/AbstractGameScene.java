package arcaym.model.game.core.scene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.controller.game.GameUser;
import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.objects.GameObjectType;

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
     * @param zIndex z index
     * @return resulting object
     */
    protected abstract GameObject createObject(GameObjectType type, int zIndex);

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
    public Collection<GameObject> getGameObjects() {
        return Collections.unmodifiableCollection(this.gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents(final GameUser user) {
        while (!this.creationEvents.isEmpty()) {
            this.createObject(this.creationEvents.removeFirst(), user);
        }
        while (!this.destroyEvents.isEmpty()) {
            this.destroyObject(this.destroyEvents.removeFirst(), user);
        }
        LOGGER.info("Finished consuming all pending events");
    }

    private void createObject(final CreationInfo creation, final GameUser user) {
        final var gameObject = this.createObject(creation.type(), creation.zIndex());
        gameObject.setPosition(creation.position());
        LOGGER.info(new StringBuilder("Created object ").append(gameObject).toString());
        this.gameObjects.add(gameObject);
        user.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject, final GameUser user) {
        this.gameObjects.remove(gameObject);
        LOGGER.info(new StringBuilder("Destroyed object ").append(gameObject).toString());
        user.destroyObject(gameObject);
    }

}
