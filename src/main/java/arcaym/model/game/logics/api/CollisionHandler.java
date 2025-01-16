package arcaym.model.game.logics.api;

import java.util.stream.Stream;

import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.objects.api.GameObjectInfo;

/**
 * Interface for a handler of collisions.
 */
public interface CollisionHandler {
    /**
     * 
     * @param objectInfo informations of an external object
     * @return wether the object is colliding with a subject
     */
    boolean isColliding(GameObjectInfo objectInfo);
    /**
     * 
     * @param scene informations regardin the whole scene
     * @return a Stream of all the objects colliding with a subject
     */
    Stream<GameObjectInfo> getCollidingObjects(GameSceneInfo scene);
}
