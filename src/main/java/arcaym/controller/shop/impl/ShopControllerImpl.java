package arcaym.controller.shop.impl;

import java.util.Map;

import arcaym.controller.game.objects.api.GameObjectsProvider;
import arcaym.controller.shop.api.ShopController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.model.user.api.UserStateInfo;

/**
 * Default implementation of {@link ShopController}.
 */
public class ShopControllerImpl implements ShopController {

    private final Shop shopModel;
    private final UserStateInfo userView;

    /**
     * Default constructor.
     * 
     * @param userView needed to read the score of the user
     * @param provider 
     */
    public ShopControllerImpl(final UserStateInfo userView, final GameObjectsProvider provider) {
        this.shopModel = new ShopImpl(provider.getLockedGameObjects(), provider.getUnlockedGameObjects());
        this.userView = userView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean requestTransaction(final GameObjectType toBuy) {
        return shopModel.makeTransaction(toBuy);
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
        return this.userView.getCredit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuy(final GameObjectType item) {
        return shopModel.canBuy(item);
    }
}
