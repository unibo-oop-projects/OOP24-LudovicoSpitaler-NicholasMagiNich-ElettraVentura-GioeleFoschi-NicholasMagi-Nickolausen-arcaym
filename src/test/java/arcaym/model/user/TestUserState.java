package arcaym.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.api.UserStateInfo;
import arcaym.model.user.impl.UserStateImpl;

class TestUserState {

    private static final int DEFAULT_CREDIT = 0;
    private static final String COPY_FILE = "user_data_backup";
    private static final String SAVES_FILE = "user_data";
    private static final Set<GameObjectType> DEFAULT_ITEMS = EnumSet.copyOf(Set.of(
        GameObjectType.USER_PLAYER,
        GameObjectType.COIN,
        GameObjectType.FLOOR,
        GameObjectType.SPIKE));

    private UserState userState;

    private void resetCredit() {
        this.userState.decrementCredit(this.userState.getCredit());
    }

    @BeforeEach
    void setup() {
        // Does not overwrite the previous states (if the player has some recent saves)
        final var serializer = new UserStateSerializerImpl();
        this.userState = new UserStateImpl();
        serializer.save(
            new UserStateInfo(
                userState.getCredit(), 
                userState.getItemsOwned(),
                DEFAULT_ITEMS, 
                userState.getPurchasedItems()), COPY_FILE);
        serializer.save(
            new UserStateInfo(
                DEFAULT_CREDIT,
                DEFAULT_ITEMS, 
                DEFAULT_ITEMS,
                Collections.emptySet()), SAVES_FILE);
    }

    @AfterEach
    void clearTraces() {
        final var serializer = new UserStateSerializerImpl();
        serializer.save(serializer.load(COPY_FILE).get(), SAVES_FILE);
        FileUtils.deleteFile(COPY_FILE.concat(".json"), "saves");
    }

    @Test
    void testCreditChange() {
        assertEquals(userState.getCredit(), DEFAULT_CREDIT);
        final var firstCreditAmount = 50;
        final var increments = 3;
        for (int i = 0; i < increments; i++) {
            userState.incrementCredit(firstCreditAmount);
        }
        final var expectedCreditAfterIncrement1 = firstCreditAmount * increments;
        assertEquals(userState.getCredit(), expectedCreditAfterIncrement1);

        final var invalidCreditAmount1 = -5;
        assertThrows(IllegalArgumentException.class, () -> userState.incrementCredit(invalidCreditAmount1));
        assertEquals(expectedCreditAfterIncrement1, userState.getCredit());

        resetCredit();
        assertEquals(userState.getCredit(), DEFAULT_CREDIT);
        this.userState.decrementCredit(firstCreditAmount);
        assertEquals(userState.getCredit(), DEFAULT_CREDIT);
        assertThrows(IllegalArgumentException.class, () -> userState.decrementCredit(invalidCreditAmount1));
    }

    @Test
    void testOwnedObjects() {
        assertTrue(userState.getPurchasedItems().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> userState.unlockNewItem(GameObjectType.COIN));

        userState.unlockNewItem(GameObjectType.WALL);
        assertEquals(Set.of(GameObjectType.WALL), userState.getPurchasedItems());
        assertTrue(userState.getItemsOwned().contains(GameObjectType.WALL));

        userState.unlockNewItem(GameObjectType.MOVING_X_OBSTACLE);
        userState.unlockNewItem(GameObjectType.MOVING_Y_OBSTACLE);
        assertEquals(Set.of(
            GameObjectType.WALL, 
            GameObjectType.MOVING_X_OBSTACLE, 
            GameObjectType.MOVING_Y_OBSTACLE), userState.getPurchasedItems());
        assertTrue(
            userState.getItemsOwned().containsAll(Set.of(
                GameObjectType.WALL, 
                GameObjectType.MOVING_X_OBSTACLE,
                GameObjectType.MOVING_Y_OBSTACLE)));
    }
}
