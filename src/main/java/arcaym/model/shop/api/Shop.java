package arcaym.model.shop.api;

import java.util.Map;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface modelling the operations made by the shop.
 */
public interface Shop {

    /**
     * Attempts to make a transaction.
     * 
     * @param toBuy item to be bought
     * @return {@code True} if the transaction ends with success. {@code False} otherwise
     */
    boolean makeTransaction(GameObjectType toBuy);

    /**
     * 
     * @return the set of the objects to be purchased
     */
    Map<GameObjectType, Integer> getLockedGameObjects();
}
