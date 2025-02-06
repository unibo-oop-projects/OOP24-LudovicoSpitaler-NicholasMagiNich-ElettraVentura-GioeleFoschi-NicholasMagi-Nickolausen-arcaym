package arcaym.model.game.core.scene;

import java.util.Collection;

import arcaym.common.geometry.Point;
import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.core.objects.GameObjectInfo;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameScene} info view.
 */
public interface GameSceneInfo {

    /**
     * Record to represent a creation event.
     * 
     * @param type game object type
     * @param position game object position
     * @param zIndex z index
     */
    record CreationInfo(GameObjectType type, Point position, int zIndex) { }

    /**
     * Remove object from the scene.
     * 
     * @param gameObject game object to remove
     */
    void scheduleDestruction(GameObject gameObject);

    /**
     * @return all game objects in the scene
     */
    Collection<? extends GameObjectInfo> getGameObjects();

}
