package arcaym;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserStateInfo;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Some utilites for multiple operations in testing.
 */
public final class TestingToolkit {

    private static final String COPY_FILE = "user_data_backup";
    private static final String SAVES_FILE = "user_data";

    // Utility class
    private TestingToolkit() { }

    /**
     * Creates a backup for the user state in a temporary file.
     */
    public static void writeUserStateBackup() {
        final var serializer = new UserStateSerializerImpl();
        serializer.save(serializer.load(COPY_FILE).get(), SAVES_FILE);
        FileUtils.deleteFile(COPY_FILE.concat(".json"), "saves");
    }

    /**
     * Writes a default user state in the saves file.
     * @param defaultCredit the initial credit of the user state to test
     */
    public static void writeUserStateDefault(final int defaultCredit) {
        final var serializer = new UserStateSerializerImpl();
        final var defaultItems = EnumSet.copyOf(Set.of(
                GameObjectType.USER_PLAYER,
                GameObjectType.COIN,
                GameObjectType.FLOOR,
                GameObjectType.SPIKE));
        serializer.save(
                new UserStateInfo(
                        defaultCredit,
                        defaultItems,
                        defaultItems,
                        Collections.emptySet()),
                SAVES_FILE);
    }

    /**
     * Loads the backup file as the current state.
     */
    public static void makeUserStateBackup() {
        final var serializer = new UserStateSerializerImpl();
        final var userState = new UserStateImpl();
        final var defaultItems = EnumSet.copyOf(Set.of(
                GameObjectType.USER_PLAYER,
                GameObjectType.COIN,
                GameObjectType.FLOOR,
                GameObjectType.SPIKE));
        serializer.save(
        new UserStateInfo(
                userState.getCredit(),
                userState.getItemsOwned(),
                defaultItems,
                userState.getPurchasedItems()),
        COPY_FILE);
    }
}
