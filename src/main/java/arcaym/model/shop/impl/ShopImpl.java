package arcaym.model.shop.impl;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import arcaym.controller.app.api.GameObjectsProvider;
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
    public ShopImpl(final Map<GameObjectType, Integer> lockedObjects) {
        this.lockedObjects = Collections.unmodifiableMap(lockedObjects);
        this.userState = new UserStateImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean makeTransaction(final GameObjectType toBuy) {
        final int price = lockedObjects.get(toBuy);
        if (canBuy(toBuy)) {
            userState.decrementCredit(price);
            userState.addNewGameObject(toBuy);
            lockedObjects.remove(toBuy);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuy(final GameObjectType item) {
        final int price = lockedObjects.get(item);
        return !userState.getPurchasedGameObjects().contains(item) && userState.getCredit() - price >= 0;
    }
}
