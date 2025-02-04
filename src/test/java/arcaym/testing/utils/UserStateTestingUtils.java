package arcaym.testing.utils;

import java.util.Collections;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.user.api.UserStateInfo;
import arcaym.model.user.impl.UserStateImpl;

/**
 * Some utilites for multiple operations in testing.
 */
public final class UserStateTestingUtils {

    private static final String COPY_FILE = "user_data_backup";
    private static final String SAVES_FILE = "user_data";

    // Utility class
    private UserStateTestingUtils() { }

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
        serializer.save(
                new UserStateInfo(
                        defaultCredit,
                        Collections.emptySet()),
                SAVES_FILE);
    }

    /**
     * Loads the backup file as the current state.
     */
    public static void makeUserStateBackup() {
        final var serializer = new UserStateSerializerImpl();
        final var userState = new UserStateImpl();
        serializer.save(
        new UserStateInfo(
                userState.getCredit(),
                userState.getPurchasedItems()),
        COPY_FILE);
    }
}
