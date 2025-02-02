package arcaym.model.shop.api;

import java.util.Map;
import java.util.Optional;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface modelling the operations made by the shop.
 */
public interface Shop {

    /**
     * Attempts to make a transaction.
     * 
     * @param toBuy item to be bought
     * @return a full optional with the price of the object bought if the transaction ends
     *         with success,
     *         an {@link Optional#empty()} otherwise
     */
    Optional<Integer> makeTransaction(GameObjectType toBuy);

    /**
     * 
     * @return the set of the objects to be purchased
     */
    Map<GameObjectType, Integer> getLockedGameObjects();

    /**
     * Tests whether the user can or cannot buy an item from the shop.
     * An item can be bought if:
     * <ol>
     *  <li>the user has <b>enough credit</b></li>
     *  <li>the user <b>does not own the item yet</b></li>
     * </ol>
     * 
     * @param item the item to test
     * @return {@code True} if the user can buy the item, {@code False} otherwise
     */
    boolean canBuy(GameObjectType item);

    /**
     * 
     * @param item
     * @return the price of the item passed as argument
     */
    int getPriceOf(GameObjectType item);
}
