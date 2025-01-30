package arcaym.controller.shop.api;

import java.util.Map;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.game.score.api.GameScoreInfo;

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
     * @return the amount of credit the user can spend.
     */
    GameScoreInfo getCredit();
}
