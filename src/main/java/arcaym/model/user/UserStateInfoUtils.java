package arcaym.model.user;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import arcaym.model.game.objects.GameObjectType;

/**
 * Utility class to gather and organize basic operations on {@link UserStateInfo}.
 */
public final class UserStateInfoUtils {

    /* Initial credit */
    private static final int DEFAULT_CREDIT = 0;

    /* Items owned by the user at the beginning of the game */
    private static final Set<GameObjectType> DEFAULT_ITEMS = EnumSet.copyOf(Set.of(
        GameObjectType.USER_PLAYER,
        GameObjectType.COIN,
        GameObjectType.FLOOR,
        GameObjectType.WIN_GOAL));

    private UserStateInfoUtils() { }

    /**
     * @return the default credit of the user
     */
    public static int defaultCredit() {
        return DEFAULT_CREDIT;
    }

    /**
     * @return the default items owned by the user
     */
    public static Set<GameObjectType> defaultItems() {
        return Collections.unmodifiableSet(DEFAULT_ITEMS);
    }

    /**
     * @return a new instance of {@link UserStateInfo} with {@link #defaultCredit()} credit
     * and {@link #defaultItems()} items
     */
    public static UserStateInfo defaultState() {
        return new UserStateInfo(DEFAULT_CREDIT, Collections.unmodifiableSet(DEFAULT_ITEMS));
    }
}
