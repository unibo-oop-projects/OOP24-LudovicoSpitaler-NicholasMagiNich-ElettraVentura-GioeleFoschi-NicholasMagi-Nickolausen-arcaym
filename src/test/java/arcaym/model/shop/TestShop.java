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

import arcaym.common.utils.TestingToolkit;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;

class TestShop {

    private static final int DEFAULT_CREDIT = 0;
    private Shop shopModel;

    @BeforeEach
    void setup() {
        TestingToolkit.makeUserStateBackup();
        TestingToolkit.writeUserStateDefault(DEFAULT_CREDIT);
        shopModel = new ShopImpl();
    }

    @AfterEach
    void clearTraces() {
        TestingToolkit.writeUserStateBackup();
    }

    @Test
    void testCanBuy() {
        // Make sure nothing can be bought with 0 credit
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::canBuy));

        final int creditRecharge = 50;
        TestingToolkit.writeUserStateDefault(creditRecharge);

        // Now the user should be able to buy something
        assertTrue(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::canBuy));
        // So he buys the WALL asset (spoiler for the following test)
        assertTrue(shopModel.makeTransaction(GameObjectType.WALL));
        // And eventually he can't buy it anymore
        assertFalse(shopModel.canBuy(GameObjectType.WALL));
    }

    @Test
    void testMakeTransaction() {
        assertFalse(shopModel.getLockedGameObjects().keySet().stream().anyMatch(shopModel::makeTransaction));

        final int creditRecharge = 50;
        TestingToolkit.writeUserStateDefault(creditRecharge);

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

        final int creditRecharge = 200;
        TestingToolkit.writeUserStateDefault(creditRecharge);

        assertTrue(shopModel.makeTransaction(GameObjectType.WALL)); // -50
        assertTrue(shopModel.makeTransaction(GameObjectType.MOVING_X_OBSTACLE)); // -30
        assertTrue(shopModel.makeTransaction(GameObjectType.MOVING_Y_OBSTACLE)); // -40
        assertEquals(shopModel.getPurchasedGameObjects(), 
            Map.of(
                GameObjectType.WALL, shopModel.getPriceOf(GameObjectType.WALL),
                GameObjectType.MOVING_X_OBSTACLE, shopModel.getPriceOf(GameObjectType.MOVING_X_OBSTACLE),
                GameObjectType.MOVING_Y_OBSTACLE, shopModel.getPriceOf(GameObjectType.MOVING_Y_OBSTACLE)
            ));
    }
}
