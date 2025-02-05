package arcaym.controller.shop.impl;

import java.util.Map;

import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.impl.AbstractController;
import arcaym.controller.shop.api.ExtendedShopController;
import arcaym.model.game.objects.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.model.user.impl.UserStateImpl;
import arcaym.view.shop.api.ShopView;

/**
 * Default implementation of {@link ExtendedShopController}.
 */
public class ShopControllerImpl extends AbstractController<ShopView> implements ExtendedShopController {

    private final Shop shopModel;

    /**
     * Default constructor.
     * 
     * @param switcher controller switcher
     */
    public ShopControllerImpl(final ControllerSwitcher switcher) {
        super(switcher);
        this.shopModel = new ShopImpl();
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
    public Map<GameObjectType, Integer> getLockedItems() {
        return shopModel.getLockedGameObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getUserCredit() {
        return new UserStateImpl().getCredit();
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
    public Map<GameObjectType, Integer> getPurchasedItems() {
        return shopModel.getPurchasedGameObjects();
    }
}
