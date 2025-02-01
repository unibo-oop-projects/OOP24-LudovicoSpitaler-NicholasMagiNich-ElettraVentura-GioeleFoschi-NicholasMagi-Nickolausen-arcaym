package arcaym.controller.game.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.controller.game.objects.api.GameObjectsProvider;
import arcaym.controller.game.objects.impl.GameObjectsProviderImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

class TestGameObjectProvider {

    private GameObjectsProvider provider;
    private UserState userTest;
    private static final Set<GameObjectType> PURCHASABLE_ITEMS = Set.of(
        GameObjectType.WALL,
        GameObjectType.MOVING_X_OBSTACLE,
        GameObjectType.MOVING_Y_OBSTACLE
    );
    
    @BeforeEach
    void setup() {
        provider = new GameObjectsProviderImpl();
        userTest = new UserStateImpl(provider.getUnlockedGameObjects());
    }

    @Test
    void testInitialState() {
        assertTrue(provider.getLockedGameObjects().keySet().containsAll(PURCHASABLE_ITEMS));
        assertEquals(Collections.emptySet(), userTest.getPurchasedGameObjects());
        assertEquals(Collections.emptySet(), provider.getUnlockedGameObjects());
    }

    @Test
    void testUnlockedGameObjects() {
        userTest.unlockNewGameObject(GameObjectType.MOVING_X_OBSTACLE);
        assertTrue(provider.getUnlockedGameObjects().contains(GameObjectType.MOVING_X_OBSTACLE));
    }
}
