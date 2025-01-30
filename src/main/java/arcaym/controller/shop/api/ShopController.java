package arcaym.controller.shop.api;

import java.util.Map;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for shop controller.
 */
public interface ShopController {

    /**
     * Request transaction to the shop model.
     * @param toBuy
     * @return {@code True} if the transaction ends with success. {@code False} otherwise
     */
    boolean requestTransaction(GameObjectType toBuy);

    /**
     * 
     * @return all the game objects to be purchased.
     */
    Map<GameObjectType, Integer> getLockedGameObjects();

    /**
     *
     * @see Shop#canBuy(GameObjectType)
     * @param item
     * @return Same as {@link Shop#canBuy(GameObjectType)}
     */
    boolean canBuy(GameObjectType item);

    /**
     * 
     * @return the amount of credit the user can spend.
     */
    int getCredit();
}
