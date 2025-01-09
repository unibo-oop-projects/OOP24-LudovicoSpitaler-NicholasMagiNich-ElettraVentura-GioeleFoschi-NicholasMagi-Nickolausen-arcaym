package arcaym.model.game.core.scene.impl;

import java.util.HashSet;
import java.util.Set;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.scene.api.GameSceneFactory;

/**
 * Basic implementation of {@link GameSceneFactory}.
 */
public class BaseGameSceneFactory implements GameSceneFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene ofObjects(final Set<GameObject> gameObjects) {
        return new BaseGameScene(gameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScene empty() {
        return this.ofObjects(new HashSet<>());
    }

}
