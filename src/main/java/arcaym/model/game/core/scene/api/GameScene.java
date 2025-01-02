package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;

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
    @FieldRepresentation
    Collection<GameObject> getObjects();

}
