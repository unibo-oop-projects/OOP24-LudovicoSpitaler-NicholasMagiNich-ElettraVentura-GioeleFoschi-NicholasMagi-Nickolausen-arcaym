package arcaym.testing.utils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;

import com.google.gson.Gson;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.api.UserStateSerializer;
import arcaym.controller.user.impl.UserStateSerializerJSON;
import arcaym.model.user.impl.UserStateImpl;
import arcaym.model.user.impl.UserStateInfo;

/**
 * Some utilites for multiple operations in testing.
 */
public final class UserStateTestingUtils {

    private static final String COPY_FILE = "user_data_backup";
    private static final String SAVES_FILE = "user_data";
    private static final String EXTENSION = ".json";

    // Utility class
    private UserStateTestingUtils() {
    }

    /* 
        Mirrors all the operations included in UserStateSerializer & UserStateSerializerJSON,
        even the private ones.
    */
    private interface UserStateSerializerTestingUtils extends UserStateSerializer {
        boolean save(UserStateInfo userState, String filename);

        Optional<UserStateInfo> load(String filename);
    }

    private static UserStateSerializerTestingUtils utilitySerializer() {
        return new UserStateSerializerTestingUtils() {

            @Override
            public boolean save(final UserStateInfo userState) {
                return this.save(userState, SAVES_FILE);
            }

            @Override
            public UserStateInfo getUpdatedState() {
                return new UserStateSerializerJSON().getUpdatedState();
            }

            @Override
            public Optional<UserStateInfo> load(final String filename) {
                final var rawState = FileUtils.readFromPath(getPathOf(filename));
                if (rawState.isEmpty()) {
                    return Optional.empty();
                }
                return FileUtils.convertToObj(UserStateInfo.class, rawState.get());
            }

            private Path getPathOf(final String fileName) {
                return Path.of(FileUtils.USER_FOLDER, fileName.concat(EXTENSION));
            }

            @Override
            public boolean save(final UserStateInfo userState, final String filename) {
                FileUtils.createUserDirectory();
                return FileUtils.writeFile(
                        filename.concat(EXTENSION),
                        FileUtils.USER_FOLDER,
                        new Gson().toJson(userState));
            }
        };
    }

    /**
     * Loads the backup file as the current state.
     */
    public static void loadUserStateBackup() {
        final var serializer = utilitySerializer();
        serializer.save(serializer.load(COPY_FILE).get());
        FileUtils.deleteFile(COPY_FILE.concat(EXTENSION), FileUtils.USER_FOLDER);
    }

    /**
     * Writes a custom user state in the saves file.
     * 
     * @param defaultCredit the initial credit of the user state to test
     */
    public static void writeTestUserState(final int defaultCredit) {
        final var serializer = utilitySerializer();
        serializer.save(
                new UserStateInfo(
                        defaultCredit,
                        Collections.emptySet()));
    }

    /**
     * Creates a backup for the user state in a temporary file.
     */
    public static void makeUserStateBackup() {
        final var serializer = utilitySerializer();
        final var userState = new UserStateImpl();
        serializer.save(
                new UserStateInfo(
                        userState.getCredit(),
                        userState.getPurchasedItems()),
                COPY_FILE);
    }
}
