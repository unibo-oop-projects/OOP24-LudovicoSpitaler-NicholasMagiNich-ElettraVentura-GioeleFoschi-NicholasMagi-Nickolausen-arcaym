package arcaym.model.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.testing.utils.UserStateTestingUtils;

class TestShop {

    private static final int DEFAULT_CREDIT = 0;
    private Shop shopModel;

    @BeforeEach
    void setup() {
        UserStateTestingUtils.makeUserStateBackup();
        UserStateTestingUtils.writeTestUserState(DEFAULT_CREDIT);
        shopModel = new ShopImpl();
    }

    @AfterEach
    void clearTraces() {
        UserStateTestingUtils.loadUserStateBackup();
    }

    @Test
    void testCanBuy() {
        // Make sure nothing can be bought with 0 credit
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::canBuy));

        final int creditRecharge = 300;
        UserStateTestingUtils.writeTestUserState(creditRecharge);

        // Now the user should be able to buy something
        assertTrue(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::canBuy));
        // So he buys the WALL asset (assuming makeTransaction works)
        assertTrue(shopModel.makeTransaction(GameObjectType.WALL));
        // And eventually he can't buy it anymore - because he already owns it
        assertFalse(shopModel.canBuy(GameObjectType.WALL));
        // He cannot afford anything else
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::canBuy));
    }

    @Test
    void testMakeTransaction() {
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::makeTransaction));

        final int creditRecharge = 300;
        UserStateTestingUtils.writeTestUserState(creditRecharge);

        // The transaction ends with success
        assertTrue(shopModel.makeTransaction(GameObjectType.WALL));
        // The user has a new asset in his collection 
        assertEquals(Set.of(GameObjectType.WALL), shopModel.getPurchasedGameObjects().keySet());
        // and not enough credit to afford anymore
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::makeTransaction));
    }

    @Test
    void testGetPurchasedGameObjects() {
        assertEquals(shopModel.getPurchasedGameObjects(), Collections.emptyMap());

        final int creditRecharge = 2100;
        UserStateTestingUtils.writeTestUserState(creditRecharge);

        assertTrue(shopModel.makeTransaction(GameObjectType.WALL));
        assertTrue(shopModel.makeTransaction(GameObjectType.MOVING_X_OBSTACLE));
        assertTrue(shopModel.makeTransaction(GameObjectType.MOVING_Y_OBSTACLE));
        assertFalse(shopModel.makeTransaction(GameObjectType.SPIKE));
        assertEquals(shopModel.getPurchasedGameObjects(), 
            Map.of(
                GameObjectType.WALL, shopModel.getPriceOf(GameObjectType.WALL),
                GameObjectType.MOVING_X_OBSTACLE, shopModel.getPriceOf(GameObjectType.MOVING_X_OBSTACLE),
                GameObjectType.MOVING_Y_OBSTACLE, shopModel.getPriceOf(GameObjectType.MOVING_Y_OBSTACLE)
            ));
    }
}
