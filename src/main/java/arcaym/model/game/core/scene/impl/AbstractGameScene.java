package arcaym.model.game.core.scene.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

import arcaym.common.point.impl.Points;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.api.GameScene;

/**
 * Abstract implementation of {@link GameScene}.
 * It implements objects access while leaving adding/removing logic.
 */
public abstract class AbstractGameScene implements GameScene {

    /**
     * Get objects collection.
     * 
     * @return game objects
     */
    protected abstract Collection<GameObject> gameObjects();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObject(final GameObject object) {
        this.gameObjects().add(Objects.requireNonNull(object));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObject(final GameObject object) {
        this.gameObjects().remove(Objects.requireNonNull(object));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<GameObject> getObjectsStream() {
        return Collections.unmodifiableCollection(this.gameObjects()).stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<GameObject> getNearObjectsStream(final GameObject searchPivot, final int searchRadius) {
        Objects.requireNonNull(searchPivot);
        return this.getObjectsStream()
            .filter(object -> Points.distance(searchPivot.getPosition(), object.getPosition()) < searchRadius);
    }

}
