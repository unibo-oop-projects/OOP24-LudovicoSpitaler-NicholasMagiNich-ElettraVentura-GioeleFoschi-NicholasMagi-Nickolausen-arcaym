package arcaym.controller.game.objects.impl;

import java.util.Map;
import java.util.Collections;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import arcaym.controller.game.objects.api.GameObjectsProvider;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Utility class to serve game objects across the application.
 */
public class GameObjectsProviderImpl implements GameObjectsProvider {

    private Set<GameObjectType> unlockedGameObjects;
    private static final Map<GameObjectType, Integer> PRICES = Map.of(
        GameObjectType.WALL, 0,
        GameObjectType.MOVING_X_OBSTACLE, 10,
        GameObjectType.MOVING_Y_OBSTACLE, 10
    ); 

    /**
     * Default constructor.
     */
    public GameObjectsProviderImpl() {
        retrieveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getUnlockedGameObjects() {
        retrieveData();
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

    private void retrieveData() {
        final var loadedSave = new UserStateSerializerImpl().load();
        this.unlockedGameObjects = loadedSave.isPresent() ? 
            loadedSave.get().getPurchasedGameObjects() : 
            Collections.emptySet();
    }
}
