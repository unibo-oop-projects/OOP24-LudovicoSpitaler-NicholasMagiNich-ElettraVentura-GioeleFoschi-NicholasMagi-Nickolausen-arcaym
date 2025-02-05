package arcaym.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.model.game.objects.GameObjectType;
import arcaym.testing.utils.UserStateTestingUtils;

class TestUserState {

    private static final int DEFAULT_CREDIT = 0;

    private UserState userState;

    private void resetCredit() {
        this.userState.decrementCredit(this.userState.getCredit());
    }

    @BeforeEach
    void setup() {
        // Does not overwrite the previous states (if the player has some recent saves)
        UserStateTestingUtils.makeUserStateBackup();
        UserStateTestingUtils.writeTestUserState(DEFAULT_CREDIT);
        this.userState = new UserStateImpl();
    }

    @AfterEach
    void clearTraces() {
        UserStateTestingUtils.loadUserStateBackup();
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
    void testOwnedItems() {
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
