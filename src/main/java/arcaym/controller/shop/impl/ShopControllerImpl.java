package arcaym.controller.shop.impl;

import java.util.Map;

import arcaym.controller.editor.api.GameObjectsProvider;
import arcaym.controller.editor.impl.GameObjectsProviderImpl;
import arcaym.controller.shop.api.ShopController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.model.user.api.UserStateInfo;

/**
 * Default implementation of the shop controller.
 */
public class ShopControllerImpl implements ShopController {

    private final Shop shopModel;
    private final GameObjectsProvider provider;
    private final UserStateInfo userView;

    /**
     * Default constructor.
     * 
     * @param userView the score of 
     */
    public ShopControllerImpl(final UserStateInfo userView) {
        this.shopModel = new ShopImpl();
        this.provider = new GameObjectsProviderImpl();
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
        return provider.getLockedGameObjects();
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
