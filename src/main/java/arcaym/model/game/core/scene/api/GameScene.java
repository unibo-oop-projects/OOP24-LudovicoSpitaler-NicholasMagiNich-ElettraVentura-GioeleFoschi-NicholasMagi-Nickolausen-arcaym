package arcaym.model.game.core.scene.api;

import java.util.Collection;
import java.util.stream.Stream;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.impl.GameSceneFactory;

/**
 * Interface for a game objects manager.
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
     * @return a {@link Stream} of the game objects
     */
    Stream<GameObject> getObjectsStream();

    /**
     * Get all objects within the search radius of an object.
     * 
     * @param searchPivot center of the search
     * @param searchRadius max search distance
     * @return a {@link Stream} of the game objects within the radius
     */
    Stream<GameObject> getNearObjectsStream(GameObject searchPivot, int searchRadius);

    /**
     * Interface for a {@link GameScene} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new GameSceneFactory();
        }

        /**
         * Create a scene that uses the provided collection.
         * 
         * @param collection game objects collection
         * @return resulting scene
         */
        GameScene ofCollection(Collection<GameObject> collection);

        /**
         * Create a scene where the same game object is not allowed twice.
         * 
         * @return game scene
         */
        GameScene ofDistinctObjects();

    }

}
