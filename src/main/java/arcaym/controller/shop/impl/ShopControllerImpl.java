package arcaym.controller.shop.impl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import arcaym.controller.shop.api.ShopController;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.shop.api.Shop;
import arcaym.model.shop.impl.ShopImpl;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Default implementation of the shop controller.
 */
public class ShopControllerImpl implements ShopController {

    private final Shop shopModel;
    private final UserState userModel;

    /**
     * Default constructor.
     */
    public ShopControllerImpl() {
        this.shopModel = new ShopImpl();
        this.userModel = new UserStateImpl();
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
    public Set<GameObjectType> getUnlockedGameObjects() {
        return userModel.getPurchasedGameObjects();
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
    public Set<GameObjectType> getAllGameObjects() {
        return Stream.concat(getUnlockedGameObjects().stream(), getLockedGameObjects().keySet().stream())
            .collect(Collectors.toSet());
    }

}
