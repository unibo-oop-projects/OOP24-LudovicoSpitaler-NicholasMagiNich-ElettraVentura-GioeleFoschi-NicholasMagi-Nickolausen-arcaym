package arcaym.controller.game.core.scene.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameScene} manager.
 */
public interface GameSceneManager extends GameScene {

    /**
     * Consume pending scene events.
     */
    void consumePendingActions();

    /**
     * Get all objects in the scene.
     * 
     * @return game objects
     */
    Collection<GameObject> gameObjects();

}
