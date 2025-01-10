package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;

/**
 * Interface for a game scene.
 */
public interface GameScene {

    /**
     * Add object to the scene.
     * 
     * @param object game object to add
     */
    void addObject(GameObject object);

    /**
     * Remove object from the scene.
     * 
     * @param object game object to remove
     */
    void removeObject(GameObject object);

    /**
     * Get all objects in the scene.
     * 
     * @return game objects
     */
    Collection<GameObjectView> getObjects();

}
