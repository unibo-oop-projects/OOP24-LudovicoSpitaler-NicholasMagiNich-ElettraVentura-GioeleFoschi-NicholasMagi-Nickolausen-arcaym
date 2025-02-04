package arcaym.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.api.UserStateSerializer;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserStateInfo;

class TestUserStateSerializer {

    private UserStateSerializer serializer;
    private UserStateInfo userStateView;

    private static final String FILENAME = "user_data_testing";

    @BeforeEach
    void setup() {
        serializer = new UserStateSerializerImpl();
    }

    @AfterEach
    void clearTraces() {
        FileUtils.deleteFile(FILENAME.concat(".json"), "saves");
    }

    private void initUserState() {
        final var initialCredit = 50;
        userStateView = new UserStateInfo(
            initialCredit,
            Set.of(GameObjectType.WALL));
    }

    @Test
    void testSerializationSuccess() {
        initUserState();
        final var ok = serializer.save(userStateView, FILENAME);
        assertTrue(ok);
    }

    @Test
    void testDeserializationSuccess() {
        initUserState();
        serializer.save(userStateView, FILENAME);
        final var deserializedUserState = serializer.load(FILENAME);

        assertTrue(deserializedUserState.isPresent());
        final var content = deserializedUserState.get();
        assertEquals(userStateView, content);
        // Not necessary, records automatically override 'Object.equals()'
        assertEquals(userStateView.credit(), content.credit());
        assertEquals(userStateView.purchasedItems(), content.purchasedItems());
    }

    @Test
    void testDeserializationFailure() {
        final var deserializedUserState = serializer.load(FILENAME);
        // De-serialization must fail if nothing has been saved before.
        assertTrue(deserializedUserState.isEmpty());
    }
}
