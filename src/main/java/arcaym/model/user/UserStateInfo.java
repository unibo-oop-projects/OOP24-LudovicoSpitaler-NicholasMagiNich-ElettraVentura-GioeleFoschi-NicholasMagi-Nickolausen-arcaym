package arcaym.model.user;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

import arcaym.model.game.objects.GameObjectType;

/**
 * A read-only view of {@link UserStateManager}. 
 * This record serves primarily as a collection of information
 * that gets serialized and deserialized across the application. 
 * 
 * @param credit the credit of the user needed to buy assets from the shop
 * @param purchasedItems the collection of the items bought from the shop
 */
public record UserStateInfo(
        int credit,
        Set<GameObjectType> purchasedItems) {

    /**
     * Turns all the mutable parameters into immutable.
     * 
     * @param credit
     * @param purchasedItems
     */
    public UserStateInfo {
        purchasedItems = Collections.unmodifiableSet(purchasedItems);
    }

    /**
     * @return an immutable collection of the items owned 
     * ({@link #defaultItems()} + {@link #purchasedItems()})
     */
    public Set<GameObjectType> itemsOwned() {
        return Sets.union(UserStateInfoUtils.defaultItems(), purchasedItems);
    }

    /**
     * Needed in order to avoid exposing internal representation of fields.
     * 
     * @return an immutable view of purchasedItems()
     */
    @Override
    public Set<GameObjectType> purchasedItems() {
        return Collections.unmodifiableSet(this.purchasedItems);
    }

    /**
     * @param item
     * @return
     */
    public boolean hasItem(final GameObjectType item) {
        return this.itemsOwned().contains(item);
    }

    /**
     * Returns a new instance of {@link UserStateInfo} almost identical to the
     * original instance, except for the credit parameter.
     * 
     * @param credit
     * @return
     * @throws IllegalArgumentException if credit is negative!
     */
    public UserStateInfo withCredit(final int credit) {
        if (credit < 0) {
            throw new IllegalArgumentException("Cannot receive negative credit! (Received: " + credit + ")");
        }
        return new UserStateInfo(credit,
                this.purchasedItems());
    }

    /**
     * Returns a new instance of {@link UserStateInfo} almost identical to the
     * original instance, except for the items purchased collection parameter.
     * 
     * @param itemsOwned
     * @return
     * @throws NullPointerException if the set is null!
     */
    public UserStateInfo withPurchasedItems(final Set<GameObjectType> purchasedItems) {
        return new UserStateInfo(
                this.credit(),
                Objects.requireNonNull(purchasedItems, "Cannot set null set!"));
    }
}
