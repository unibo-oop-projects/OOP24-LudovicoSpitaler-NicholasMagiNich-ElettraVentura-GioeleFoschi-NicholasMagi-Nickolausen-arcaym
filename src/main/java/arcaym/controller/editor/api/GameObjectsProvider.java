package arcaym.controller.editor.api;

import java.util.Map;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

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

    /**
     * Unlocks a new gameobject.
     */
    void unlockObject(GameObjectType toUnlock);
}
