package arcaym.controller.game.scene.api;

import java.util.Collection;
import java.util.Objects;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link GameScene} restricted view.
 */
public interface GameSceneView {

    /**
     * Record to represent a creation event.
     * 
     * @param type game object type
     * @param position game object position
     */
    record CreationInfo(GameObjectType type, Point position) { }

    /**
     * Schedule creation of an object in the scene.
     * 
     * @param type game object type
     * @param position game object position
     */
    default void scheduleCreation(final GameObjectType type, final Point position) {
        this.scheduleCreation(new CreationInfo(Objects.requireNonNull(type), Objects.requireNonNull(position)));
    }

    /**
     * Schedule creation of an object in the scene.
     * 
     * @param creationEvent creation event
     */
    void scheduleCreation(CreationInfo creationEvent);

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
