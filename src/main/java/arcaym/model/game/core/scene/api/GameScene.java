package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.impl.GameSceneFactory;

/**
 * Interface for a game objects manager.
 */
@TypeRepresentation
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
    Collection<GameObject> getObjects();

    /**
     * Get number of objects in the scene.
     * 
     * @return number of objects
     */
    @FieldRepresentation
    default int size() {
        return this.getObjects().size();
    }

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
