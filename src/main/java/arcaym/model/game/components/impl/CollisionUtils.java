package arcaym.model.game.components.impl;

import java.util.stream.Stream;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.scene.api.GameSceneInfo;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Utility class that handles collisions with borders and walls.
 */
public final class CollisionUtils {

    private CollisionUtils() { }

    /**
     * Returns wether or not there is a collision with a wall. 
     * @param gameScene game scene
     * @param gameObject game object
     * @return if it's colliding
     */
    public static boolean isWallCollisionActive(final GameSceneInfo gameScene, final GameObject gameObject) {
        return getCollidingObjects(gameScene, gameObject)
                .anyMatch(obj -> obj.type() == GameObjectType.WALL);
    }

    /**
     * Returns stream of objects colliding with gameObject.
     * @param scene
     * @param gameObject
     * @return colliding objects
     */
    public static Stream<GameObjectInfo> getCollidingObjects(final GameSceneInfo scene,
            final GameObject gameObject) {
        return scene.getGameObjectsInfos().stream()
            .filter(obj -> obj.boundaries().intersecting(gameObject.boundaries())
        );
    }
}
