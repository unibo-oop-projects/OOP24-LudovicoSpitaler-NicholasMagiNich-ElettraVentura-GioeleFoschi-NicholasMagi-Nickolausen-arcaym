package arcaym.model.user.api;

import java.util.Objects;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * A read-only view of {@link UserState}.
 */
public record UserStateInfo(int credit, Set<GameObjectType> itemsOwned, Set<GameObjectType> defaultItems, Set<GameObjectType> purchasedItems) {

    public UserStateInfo withCredit(int credit) {
        if (credit < 0) {
            throw new IllegalArgumentException("Cannot receive negative credit! (Received: " + credit + ")");
        }
        return new UserStateInfo(credit, this.itemsOwned(), this.defaultItems(), this.purchasedItems());
    }

    public UserStateInfo withItemsOwned(Set<GameObjectType> itemsOwned) {
        return new UserStateInfo(
            this.credit(),
            Objects.requireNonNull(itemsOwned, "Cannot set null set!"), 
            this.defaultItems(), 
            this.purchasedItems());
    }

    public UserStateInfo withPurchasedItems(Set<GameObjectType> purchasedItems) {
        return new UserStateInfo(
            this.credit(), 
            this.itemsOwned(), 
            this.defaultItems(), 
            Objects.requireNonNull(purchasedItems, "Cannot set null set!"));
    }
}
