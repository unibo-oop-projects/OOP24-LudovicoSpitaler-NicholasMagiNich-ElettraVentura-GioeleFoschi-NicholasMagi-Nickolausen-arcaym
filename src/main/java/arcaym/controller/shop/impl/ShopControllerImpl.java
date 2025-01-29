package arcaym.controller.shop.impl;

import java.util.Map;

import arcaym.controller.editor.api.GameObjectsProvider;
import arcaym.controller.editor.impl.GameObjectsProviderImpl;
import arcaym.controller.shop.api.ShopController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;

/**
 * Default implementation of the shop controller.
 */
public class ShopControllerImpl implements ShopController {

    private final Shop shopModel;
    private final GameObjectsProvider provider;

    /**
     * Default constructor.
     */
    public ShopControllerImpl() {
        this.shopModel = new ShopImpl();
        this.provider = new GameObjectsProviderImpl();
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
}
