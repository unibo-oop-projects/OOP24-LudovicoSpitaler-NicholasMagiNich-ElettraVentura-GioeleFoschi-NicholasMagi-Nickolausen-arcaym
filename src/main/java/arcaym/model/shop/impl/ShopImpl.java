package arcaym.model.shop.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Default implementation of interface @see Shop.
 */
public class ShopImpl implements Shop {

    private final Map<GameObjectType, Integer> lockedObjects;
    private final UserState userState;
    
    /**
     * Default constructor.
     */
    public ShopImpl() {
        this.lockedObjects = new HashMap<>();
        this.userState = new UserStateImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean makeTransaction(final GameObjectType toBuy) {
        final int price = lockedObjects.get(toBuy);
        if (userState.getScore() - price >= 0) {
            userState.decrementScore(price);
            userState.addNewGameObject(toBuy);
            this.lockedObjects.remove(toBuy);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getLockedGameObjects() {
        return Collections.unmodifiableMap(lockedObjects);
    }
}
