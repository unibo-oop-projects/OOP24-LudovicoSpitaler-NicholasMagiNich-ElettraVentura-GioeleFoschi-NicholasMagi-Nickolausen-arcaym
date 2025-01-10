package arcaym.model.game.core.scene.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.core.scene.api.GameScene;

/**
 * Implementation of {@link GameScene} using a {@link Set} of game objects.
 */
@TypeRepresentation
public class UniqueObjectsGameScene implements GameScene {

    private final Set<GameObject> gameObjects = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public Collection<GameObjectView> getObjects() {
        return Collections.unmodifiableSet(gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObject(final GameObject object) {
        this.gameObjects.add(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObject(final GameObject object) {
        this.gameObjects.remove(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
