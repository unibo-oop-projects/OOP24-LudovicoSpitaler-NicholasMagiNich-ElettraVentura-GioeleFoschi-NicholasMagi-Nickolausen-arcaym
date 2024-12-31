package arcaym.model.game.core.scene.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import arcaym.common.utils.representation.StringRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.api.GameScene;

/**
 * Basic implementation of {@link GameScene.Factory}.
 */
public class GameSceneFactory implements GameScene.Factory {

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene ofCollection(final Collection<GameObject> gameObjects) {
        Objects.requireNonNull(gameObjects);
        return new GameScene() {
            @Override
            public void addObject(final GameObject object) {
                gameObjects.add(Objects.requireNonNull(object));
            }
            @Override
            public void removeObject(final GameObject object) {
                gameObjects.remove(Objects.requireNonNull(object));
            }
            @Override
            public Collection<GameObject> getObjects() {
                return Collections.unmodifiableCollection(gameObjects);
            }
            @Override
            public String toString() {
                return StringRepresentation.ofObject(this);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene ofDistinctObjects() {
        return this.ofCollection(new HashSet<>());
    }

}
