package arcaym.model.shop.impl;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Default implementation of interface {@link Shop}.
 */
public class ShopImpl implements Shop {

    private static final Map<GameObjectType, Integer> PRICES = new EnumMap<>(Map.of(
        GameObjectType.WALL, 300,
        GameObjectType.SPIKE, 400,
        GameObjectType.MOVING_X_OBSTACLE, 900,
        GameObjectType.MOVING_Y_OBSTACLE, 900
    ));

    private final Map<GameObjectType, Integer> lockedObjects;
    private final UserState userState;

    /**
     * Default constructor.
     */
    public ShopImpl() {
        this.userState = new UserStateImpl();
        this.lockedObjects = new EnumMap<>(PRICES);
        // Removes from the locked objects all the ones bought from the user. 
        this.userState.getPurchasedItems().forEach(item -> {
            // Does not throw an Exception if the item was not mapped.
            this.lockedObjects.remove(item);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean makeTransaction(final GameObjectType toBuy) {
        final int price = lockedObjects.get(toBuy);
        if (canBuy(toBuy)) {
            userState.decrementCredit(price);
            userState.unlockNewItem(toBuy);
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
        if (!lockedObjects.containsKey(item)) {
            return false;
        }
        final int price = lockedObjects.get(item);
        return !userState.getItemsOwned().contains(item) && userState.getCredit() - price >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriceOf(final GameObjectType item) {
        if (!PRICES.containsKey(item)) {
            throw new IllegalArgumentException(item + " not included in the purchasable assets collection!");
        }
        return PRICES.get(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getPurchasedGameObjects() {
        return userState.getPurchasedItems().stream()
            .collect(Collectors.toMap(Function.identity(), this::getPriceOf));
    }
}
