package arcaym.model.game.core.objects;

import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameObject} factory.
 */
public interface GameObjectsFactory {

    /**
     * Create game object of a specific type.
     * 
     * @param type game object type
     * @param zIndex z index
     * @return resulting game object
     */
    GameObject ofType(GameObjectType type, int zIndex);

}
