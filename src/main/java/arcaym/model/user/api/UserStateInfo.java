package arcaym.model.user.api;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * A read-only view of {@link UserState}.
 * 
 * @param credit the credit of the user needed to buy assets from the shop
 * @param itemsOwned the collection of the items owned by the user (purchased + default)
 * @param defaultItems the collection of the default items owned by the user at the beginning of the game
 * @param purchasedItems the collection of the items bought from the shop
 */
public record UserStateInfo(
        int credit,
        Set<GameObjectType> itemsOwned,
        Set<GameObjectType> defaultItems,
        Set<GameObjectType> purchasedItems) {

    /**
     * Turns all the mutable parameters into immutable.
     * 
     * @param credit
     * @param itemsOwned
     * @param defaultItems
     * @param purchasedItems
     */
    public UserStateInfo {
        itemsOwned = Collections.unmodifiableSet(itemsOwned);
        defaultItems = Collections.unmodifiableSet(defaultItems);
        purchasedItems = Collections.unmodifiableSet(purchasedItems);
    }

    /**
     * 
     * @return an immutable view of defaultItems()
     */
    public Set<GameObjectType> defaultItems() {
        return Collections.unmodifiableSet(defaultItems);
    }

    /**
     * 
     * @return an immutable view of purchasedItems()
     */
    public Set<GameObjectType> purchasedItems() {
        return Collections.unmodifiableSet(purchasedItems);
    }

    /**
     * 
     * @return an immutable view of itemsOwned()
     */
    public Set<GameObjectType> itemsOwned() {
        return Collections.unmodifiableSet(itemsOwned);
    }

    /**
     * Returns a new instance of {@link UserStateInfo} almost identical to the
     * original
     * instance, except for the credit parameter.
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
                Collections.unmodifiableSet(this.itemsOwned()),
                Collections.unmodifiableSet(this.defaultItems()),
                Collections.unmodifiableSet(this.purchasedItems()));
    }

    /**
     * Returns a new instance of {@link UserStateInfo} almost identical to the
     * original
     * instance, except for the items owned collection parameter.
     * 
     * @param itemsOwned
     * @return
     * @throws NullPointerException if the set is null!
     */
    public UserStateInfo withItemsOwned(final Set<GameObjectType> itemsOwned) {
        return new UserStateInfo(
                this.credit(),
                Objects.requireNonNull(itemsOwned, "Cannot set null set!"),
                Collections.unmodifiableSet(this.defaultItems()),
                Collections.unmodifiableSet(this.purchasedItems()));
    }

    /**
     * Returns a new instance of {@link UserStateInfo} almost identical to the
     * original
     * instance, except for the items purchased collection parameter.
     * 
     * @param itemsOwned
     * @return
     * @throws NullPointerException if the set is null!
     */
    public UserStateInfo withPurchasedItems(final Set<GameObjectType> purchasedItems) {
        return new UserStateInfo(
                this.credit(),
                Collections.unmodifiableSet(this.itemsOwned()),
                Collections.unmodifiableSet(this.defaultItems()),
                Objects.requireNonNull(purchasedItems, "Cannot set null set!"));
    }
}
