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
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of {@link GameSceneManager} that uses a {@link GameObjectFactory}.
 */
public class FactoryBasedGameSceneManager implements GameSceneManager {

    private record CreationEvent(GameObjectType type, Point position) { }

    private final Set<GameObject> gameObjects = new HashSet<>();

    private List<CreationEvent> creationEvents = new ArrayList<>();
    private List<GameObject> destroyEvents = new ArrayList<>();
    private final GameObjectFactory factory;

    /**
     * Initialize the game scene manager with the given objects and factory.
     * 
     * @param startingObjects starting game objects
     * @param factory game objects factory
     */
    public FactoryBasedGameSceneManager(final GameObjectFactory factory) {
        this.factory = Objects.requireNonNull(factory);
    }

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

    private void createObject(final CreationEvent creationEvent) {
        final var gameObject = this.factory.ofType(creationEvent.type);
        gameObject.setPosition(creationEvent.position);
        this.gameObjects.add(gameObject);
    }

    private void destroyObject(final GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

}
