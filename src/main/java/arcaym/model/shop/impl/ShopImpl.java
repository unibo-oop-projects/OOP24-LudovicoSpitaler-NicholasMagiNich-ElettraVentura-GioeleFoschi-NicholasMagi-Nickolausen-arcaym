package arcaym.model.shop.impl;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Default implementation of interface {@link Shop}.
 */
public class ShopImpl implements Shop {

    private static final Map<GameObjectType, Integer> PRICES = new EnumMap<>(Map.of(
        GameObjectType.WALL, 50,
        GameObjectType.MOVING_X_OBSTACLE, 30,
        GameObjectType.MOVING_Y_OBSTACLE, 40
    ));
    private final Map<GameObjectType, Integer> lockedObjects;
    private final UserState userState;

    /**
     * Default constructor.
     * 
     * @param lockedObjects
     */
    public ShopImpl() {
        this.userState = new UserStateImpl();
        this.lockedObjects = new EnumMap<>(GameObjectType.class);
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> makeTransaction(final GameObjectType toBuy) {
        final int price = lockedObjects.get(toBuy);
        if (canBuy(toBuy)) {
            userState.decrementCredit(price);
            userState.unlockNewItem(toBuy);
            return Optional.of(price);
        }
        return Optional.empty();
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
        return !userState.getItemsOwned().contains(item) && userState.getCredit() - price >= 0;
    }

    @Override
    public int getPriceOf(GameObjectType item) {
        return PRICES.get(item);
    }
}
