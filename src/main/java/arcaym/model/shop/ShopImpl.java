package arcaym.model.shop;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import arcaym.model.game.objects.GameObjectType;
import arcaym.model.user.UserStateManager;
import arcaym.model.user.UserStateManagerImpl;

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

    private final UserStateManager userState;

    /**
     * Default constructor.
     */
    public ShopImpl() {
        this.userState = new UserStateManagerImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean makeTransaction(final GameObjectType toBuy) {
        final int price = this.getLockedGameObjects().get(toBuy);
        if (this.canBuy(toBuy)) {
            this.userState.decrementCredit(price);
            this.userState.unlockNewItem(toBuy);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getLockedGameObjects() {
        final Map<GameObjectType, Integer> out = new EnumMap<>(PRICES);
        // Removes from the locked objects all the ones bought from the user. 
        this.userState.getPurchasedItems().forEach(item -> {
            // Does not throw an Exception if the item was not mapped.
            out.remove(item);
        });
        return Collections.unmodifiableMap(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuy(final GameObjectType item) {
        if (this.isBought(item)) {
            return false;
        }
        final int price = this.getLockedGameObjects().get(item);
        return !this.userState.hasItem(item) && userState.getCredit() - price >= 0;
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
    public boolean isBought(final GameObjectType item) {
        return !this.getLockedGameObjects().containsKey(item);
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
