package arcaym.view.shop;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Record of product informations.
 * @param type
 * @param price
 */
public record ProductInfo(GameObjectType type, Integer price) {

}
