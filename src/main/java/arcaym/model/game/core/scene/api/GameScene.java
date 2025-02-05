package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.controller.game.api.GameObserver;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneInfo {

    /**
     * Consume pending scene events and notify game observer.
     * 
     * @param observer game observer
     */
    void consumePendingActions(GameObserver observer);

    /**
     * Get all objects in the scene.
     * 
     * @return game objects
     */
    Collection<GameObject> getGameObjects();

}
