package arcaym.controller.shop;

import java.util.Map;

import arcaym.controller.app.AbstractController;
import arcaym.controller.app.ControllerSwitcher;
import arcaym.controller.user.UserStateSerializerInfo;
import arcaym.controller.user.UserStateSerializerJSON;
import arcaym.model.game.objects.GameObjectType;
import arcaym.model.shop.Shop;
import arcaym.model.shop.ShopImpl;
import arcaym.view.shop.ShopView;

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
        return this.shopModel.makeTransaction(toBuy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getLockedItems() {
        return this.shopModel.getLockedGameObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getUserCredit() {
        final UserStateSerializerInfo serializer = new UserStateSerializerJSON(); 
        return serializer.getUpdatedState().credit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canBuy(final GameObjectType item) {
        return this.shopModel.canBuy(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<GameObjectType, Integer> getPurchasedItems() {
        return this.shopModel.getPurchasedGameObjects();
    }
}
