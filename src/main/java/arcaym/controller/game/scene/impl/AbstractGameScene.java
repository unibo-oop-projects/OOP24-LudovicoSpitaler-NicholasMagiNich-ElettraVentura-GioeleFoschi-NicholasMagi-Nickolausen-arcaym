package arcaym.controller.game.scene.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import arcaym.controller.game.scene.api.GameScene;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

/**
 * Abstract implementation of {@link GameScene}.
 * It provides events handling while leaving the creation of the objects.
 */
public abstract class AbstractGameScene implements GameScene {

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
        this.creationEvents.add(creation);
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
    public void consumePendingActions(final GameView gameView) {
        creationEvents.forEach(c -> this.createObject(c, gameView));
        destroyEvents.forEach(d -> this.destroyObject(d, gameView));
    }

    private void createObject(final CreationInfo creation, final GameView gameView) {
        final var gameObject = this.createObject(creation.type());
        gameObject.setPosition(creation.position());
        gameView.createObject(gameObject);
    }

    private void destroyObject(final GameObject gameObject, final GameView gameView) {
        this.gameObjects.remove(gameObject);
        gameView.destroyObject(gameObject);
    }

}
