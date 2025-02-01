package arcaym.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

class TestUserState {

    private UserState userState;
    private static final int DEFAULT_CREDIT = 0;

    private void resetCredit() {
        this.userState.decrementCredit(this.userState.getCredit());
    }

    @BeforeEach
    void setup() {
        userState = new UserStateImpl();
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
        // May be removed if the user state has been serialized before.
        assertTrue(userState.getPurchasedItems().isEmpty());
        userState.unlockNewItem(GameObjectType.COIN, 0);
        userState.unlockNewItem(GameObjectType.COIN, 0);
        assertEquals(1, userState.getPurchasedItems().size());
        assertTrue(userState.getPurchasedItems().keySet().contains(GameObjectType.COIN));

        userState.unlockNewItem(GameObjectType.FLOOR, 0);
        userState.unlockNewItem(GameObjectType.WALL, 0);
        userState.unlockNewItem(GameObjectType.WIN_GOAL, 0);
        assertEquals(
            Set.of(GameObjectType.WIN_GOAL, GameObjectType.WALL, GameObjectType.FLOOR, GameObjectType.COIN),
            userState.getPurchasedItems());
    }
}
