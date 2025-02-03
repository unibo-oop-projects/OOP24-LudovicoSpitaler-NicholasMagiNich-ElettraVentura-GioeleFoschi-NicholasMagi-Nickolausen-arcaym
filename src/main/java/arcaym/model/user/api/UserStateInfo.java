package arcaym.model.user.api;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * A read-only view of {@link UserState}. 
 * This record serves primarily as a collection of information
 * that gets serialized and deserialized across the application. To obtain the
 * updated version of these information, use {@link UserStateView}. 
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
     * Needed in order to avoid exposing internal representation of fields.
     * 
     * @return an immutable view of defaultItems()
     */
    @Override
    public Set<GameObjectType> defaultItems() {
        return Collections.unmodifiableSet(defaultItems);
    }

    /**
     * Needed in order to avoid exposing internal representation of fields.
     * 
     * @return an immutable view of purchasedItems()
     */
    @Override
    public Set<GameObjectType> purchasedItems() {
        return Collections.unmodifiableSet(purchasedItems);
    }

    /**
     * Needed in order to avoid exposing internal representation of fields.
     * 
     * @return an immutable view of itemsOwned()
     */
    @Override
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
                this.itemsOwned(),
                this.defaultItems(),
                this.purchasedItems());
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
                this.defaultItems(),
                this.purchasedItems());
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
                this.itemsOwned(),
                this.defaultItems(),
                Objects.requireNonNull(purchasedItems, "Cannot set null set!"));
    }
}
