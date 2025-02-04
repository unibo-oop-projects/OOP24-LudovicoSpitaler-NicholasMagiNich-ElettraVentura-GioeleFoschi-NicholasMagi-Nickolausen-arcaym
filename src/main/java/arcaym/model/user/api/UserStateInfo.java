package arcaym.model.user.api;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Sets;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * A read-only view of {@link UserState}. 
 * This record serves primarily as a collection of information
 * that gets serialized and deserialized across the application. To obtain the
 * updated version of these information, use {@link UserStateView}. 
 * 
 * @param credit the credit of the user needed to buy assets from the shop
 * @param purchasedItems the collection of the items bought from the shop
 */
public record UserStateInfo(
        int credit,
        Set<GameObjectType> purchasedItems) {

    /* Initial credit */
    private static final int DEFAULT_CREDIT = 0;

    /* Items owned by the user at the beginning of the game */
    private static final Set<GameObjectType> DEFAULT_ITEMS = EnumSet.copyOf(Set.of(
        GameObjectType.USER_PLAYER,
        GameObjectType.COIN,
        GameObjectType.FLOOR,
        GameObjectType.SPIKE,
        GameObjectType.WIN_GOAL));

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
     * Creates a default user state.
     * 
     * @return a default user state with {@link #DEFAULT_CREDIT} credit and an empty collection 
     * of purchased assets 
     */
    public static UserStateInfo getDefaultState() {
        return new UserStateInfo(DEFAULT_CREDIT, Collections.emptySet());
    }

    /**
     * Needed in order to avoid exposing internal representation of fields.
     * 
     * @return an immutable view of defaultItems()
     */
    public static Set<GameObjectType> getDefaultItems() {
        return Collections.unmodifiableSet(DEFAULT_ITEMS);
    }

    /**
     * 
     * @return an immutable collection of the items owned at the beginning of the game
     */
    public Set<GameObjectType> getItemsOwned() {
        return Sets.union(DEFAULT_ITEMS, purchasedItems);
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
                Objects.requireNonNull(purchasedItems, "Cannot set null set!"));
    }
}
