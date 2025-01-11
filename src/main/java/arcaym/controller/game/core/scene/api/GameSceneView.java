package arcaym.controller.game.core.scene.api;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link GameScene} restricted view.
 */
public interface GameSceneView {

    /**
     * Spawn object in the scene.
     * 
     * @param type game object type
     * @param position game object position
     */
    void scheduleCreation(GameObjectType type, Point position);

    /**
     * Remove object from the scene.
     * 
     * @param gameObject game object to remove
     */
    void scheduleDestruction(GameObject gameObject);

    /**
     * Get a view of all objects in the scene.
     * 
     * @return game objects
     */
    Collection<GameObjectView> getObjects();

}
