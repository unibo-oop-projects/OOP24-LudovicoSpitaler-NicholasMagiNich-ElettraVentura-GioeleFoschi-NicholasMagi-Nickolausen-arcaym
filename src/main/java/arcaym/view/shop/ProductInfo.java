package arcaym.view.shop;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Record of product informations.
 */
public record ProductInfo(GameObjectType type, Integer price) {

}
