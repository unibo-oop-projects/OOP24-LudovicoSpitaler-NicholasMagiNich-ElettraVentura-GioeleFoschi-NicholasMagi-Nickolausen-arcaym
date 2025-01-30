package arcaym.controller.app.api;

import java.util.Map;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Provides all the game objects across the application.
 */
public interface GameObjectsProvider {

    /**
     * 
     * @return all the game objects purchased by the user.
     */
    Set<GameObjectType> getUnlockedGameObjects();

    /**
     * 
     * @return all the game objects to be purchased.
     */
    Map<GameObjectType, Integer> getLockedGameObjects();
}
