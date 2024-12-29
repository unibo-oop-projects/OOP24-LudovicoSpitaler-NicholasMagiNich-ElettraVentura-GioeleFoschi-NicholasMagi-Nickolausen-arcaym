package arcaym.model.game.core.scene.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
    public GameScene ofCollection(final Collection<GameObject> collection) {
        Objects.requireNonNull(collection);
        return new AbstractGameScene() {
            @Override
            protected Collection<GameObject> gameObjects() {
                return collection;
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
