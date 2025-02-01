package arcaym.controller.shop.impl;

import java.util.Collections;
import java.util.Map;

import arcaym.controller.shop.api.ShopController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Default implementation of {@link ShopController}.
 */
public class ShopControllerImpl implements ShopController {

    private final Shop shopModel;
    private final UserState userState;

    /**
     * Default constructor.
     * 
     * @param userView needed to read the score of the user
     * @param provider 
     */
    public ShopControllerImpl() {
        this.shopModel = new ShopImpl();
        this.userState = new UserStateImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requestTransaction(final GameObjectType toBuy) {
        return shopModel.makeTransaction(toBuy).isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getLockedGameObjects() {
        return shopModel.getLockedGameObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        return userState.getCredit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuy(final GameObjectType item) {
        return shopModel.canBuy(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getPurchasedGameObjects() {
        // return Collections.unmodifiableMap(userView.getPurchasedItems());
        return null;
    }
}
