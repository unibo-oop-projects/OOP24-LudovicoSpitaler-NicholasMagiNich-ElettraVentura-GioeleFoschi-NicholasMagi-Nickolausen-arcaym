package arcaym.controller.editor.impl;

import java.util.Map;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import arcaym.controller.editor.api.GameObjectsProvider;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Utility class to serve game objects across the application.
 */
public class GameObjectsProviderImpl implements GameObjectsProvider {

    private final Set<GameObjectType> unlockedGameObjects;
    private static final Map<GameObjectType, Integer> prices = Map.of(
        GameObjectType.USER_PLAYER, 0,
        GameObjectType.WALL, 0,
        GameObjectType.SPIKE, 0,
        GameObjectType.COIN, 0,
        GameObjectType.MOVING_X_OBSTACLE, 10,
        GameObjectType.MOVING_Y_OBSTACLE, 10
    ); 

    public GameObjectsProviderImpl() {
        this.unlockedGameObjects = new HashSet<>();
    }

    @Override
    public Set<GameObjectType> getUnlockedGameObjects() {
        return Collections.unmodifiableSet(unlockedGameObjects);
    }

    @Override
    public Map<GameObjectType, Integer> getLockedGameObjects() {
        return prices.entrySet().stream()
            .filter(e -> !getUnlockedGameObjects().contains(e.getKey()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    @Override
    public void unlockObject(final GameObjectType toUnlock) {
        unlockedGameObjects.add(toUnlock);
        /* TODO: WRITE TO FILE */
    }

}
