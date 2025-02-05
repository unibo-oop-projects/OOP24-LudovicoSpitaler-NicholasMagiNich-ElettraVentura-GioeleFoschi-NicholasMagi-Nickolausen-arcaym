package arcaym.controller.shop.api;

import java.util.Map;

import arcaym.controller.app.api.Controller;
import arcaym.model.game.objects.GameObjectType;
import arcaym.model.shop.api.Shop;

/**
 * Interface for a shop controller.
 */
public interface ShopController extends Controller {

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
    Map<GameObjectType, Integer> getLockedItems();

    /**
     * 
     * @return all the game objects purchased from the shop.
     */
    Map<GameObjectType, Integer> getPurchasedItems();

    /**
     *
     * @see Shop#canBuy(GameObjectType)
     * @param item
     * @return Same as {@link Shop}
     */
    boolean canBuy(GameObjectType item);

    /**
     * 
     * @return the amount of credit the user can spend.
     */
    int getUserCredit();

}
