package arcaym.controller.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.shop.api.UserStateSerializer;
import arcaym.controller.shop.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

public class TestUserStateSerializer {
    
    private UserStateSerializer serializer;
    private UserState userState;
    private static final String FILENAME = "user_data.json";

    @BeforeEach
    void setup() {
        serializer = new UserStateSerializerImpl();
        userState = new UserStateImpl();
        FileUtils.deleteFile(FILENAME, FileUtils.SAVES_FOLDER);
    }

    @Test
    void testSerializationSuccess() {
        final var initialCredit = 50;
        userState.addNewGameObject(GameObjectType.COIN);
        userState.addNewGameObject(GameObjectType.WALL);
        userState.addNewGameObject(GameObjectType.SPIKE);
        userState.incrementCredit(initialCredit);
        final var ok = serializer.save(userState);
        
        assertTrue(ok);
    }

    @Test
    void testDeserializationSuccess() {
        final var initialCredit = 50;
        userState.addNewGameObject(GameObjectType.COIN);
        userState.addNewGameObject(GameObjectType.WALL);
        userState.addNewGameObject(GameObjectType.SPIKE);
        userState.incrementCredit(initialCredit);
        serializer.save(userState);
        final var deserializedUserState = serializer.load();

        assertTrue( deserializedUserState.isPresent());
        assertEquals(userState.getCredit(), deserializedUserState.get().getCredit());
        assertTrue(deserializedUserState.get().getPurchasedGameObjects().containsAll(userState.getPurchasedGameObjects()));
        assertEquals(userState.getPurchasedGameObjects().size(), deserializedUserState.get().getPurchasedGameObjects().size());
    }

    @Test
    void testDeserializationFailure() {
        final var deserializedUserState = serializer.load();
    }
}
