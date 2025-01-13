package arcaym.model.game.core.objects.api;

import arcaym.controller.game.core.objects.api.GameObjectType;

/**
 * Interface for a {@link GameObject} factory.
 */
public interface GameObjectFactory {

    /**
     * Create game object of a specific type.
     * 
     * @param type game object type
     * @return resulting game object
     */
    GameObject ofType(GameObjectType type);

}
