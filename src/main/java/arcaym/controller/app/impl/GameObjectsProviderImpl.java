package arcaym.controller.app.impl;

import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import arcaym.controller.app.api.GameObjectsProvider;
import arcaym.controller.shop.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Utility class to serve game objects across the application.
 */
public class GameObjectsProviderImpl implements GameObjectsProvider {

    private final Set<GameObjectType> unlockedGameObjects;
    private static final Map<GameObjectType, Integer> PRICES = Map.of(
        GameObjectType.USER_PLAYER, 0,
        GameObjectType.WALL, 0,
        GameObjectType.SPIKE, 0,
        GameObjectType.COIN, 0,
        GameObjectType.MOVING_X_OBSTACLE, 10,
        GameObjectType.MOVING_Y_OBSTACLE, 10
    ); 

    /**
     * Default constructor.
     */
    public GameObjectsProviderImpl() {
        this.unlockedGameObjects = new UserStateSerializerImpl()
            .load()
            .get()
            .getPurchasedGameObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getUnlockedGameObjects() {
        return Collections.unmodifiableSet(unlockedGameObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getLockedGameObjects() {
        return PRICES.entrySet().stream()
            .filter(e -> !getUnlockedGameObjects().contains(e.getKey()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }
}
