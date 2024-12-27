package arcaym.model.game.core.world.api;

import java.util.function.Predicate;
import java.util.stream.Stream;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a game objects manager.
 */
public interface GameScene {

    /**
     * Add object to the scene.
     * 
     * @param obj game object to add
     */
    void addObject(GameObject obj);

    /**
     * Remove object from the scene.
     * 
     * @param obj game object to remove
     */
    void removeObject(GameObject obj);

    /**
     * Get all objects in the scene.
     * 
     * @return a {@link Stream} of game objects
     */
    Stream<GameObject> objectsStream();

    /**
     * Get all objects in the scene other that the one passed.
     * 
     * @param obj object to exclude
     * @return a {@link Stream} of game objects
     */
    default Stream<GameObject> otherObjectsStream(final GameObject obj) {
        return this.objectsStream()
                .filter(Predicate.not(obj::equals));
    }

}
