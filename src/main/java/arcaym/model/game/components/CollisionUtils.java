package arcaym.model.game.components;

import java.util.stream.Stream;

import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.core.objects.GameObjectInfo;
import arcaym.model.game.core.scene.GameSceneInfo;
import arcaym.model.game.objects.GameObjectType;

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
    public static Stream<? extends GameObjectInfo> getCollidingObjects(final GameSceneInfo scene,
            final GameObject gameObject) {
        return scene.getGameObjects().stream()
            .filter(obj -> obj.boundaries().intersecting(gameObject.boundaries())
        );
    }
}
