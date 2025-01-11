package arcaym.controller.game.core.scene.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneView {

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
