package arcaym.model.game.core.scene.impl;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.core.scene.api.GameScene;

/**
 * Basic implementation of {@link GameScene}.
 */
@TypeRepresentation
public class BaseGameScene implements GameScene {

    private final Set<GameObject> gameObjects;

    BaseGameScene(final Set<GameObject> gameObjects) {
        this.gameObjects = Objects.requireNonNull(gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public Set<GameObjectView> getObjects() {
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
