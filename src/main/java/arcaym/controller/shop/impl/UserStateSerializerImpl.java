package arcaym.controller.shop.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.file.FileUtils;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;
import arcaym.controller.shop.api.UserStateSerializer;

/**
 * Implementation of {@link UserStateSerializer}. 
 */
public class UserStateSerializerImpl implements UserStateSerializer {

    private static final String EXTENSION = ".json";
    private static final String FILENAME = "user_data";
    private static final Path FILE_PATH = Path.of(FileUtils.SAVES_FOLDER, FILENAME.concat(EXTENSION));

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStateSerializer.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final UserState userState) {
        try {
            Files.writeString(
                FILE_PATH,
                new Gson().toJson(userState),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("An error occurred while WRITING '" + FILENAME + "' file.", e);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<? extends UserState> load() {
        final var rawState = FileUtils.readFromPath(FILE_PATH);
        if (rawState.isEmpty()) {
            LOGGER.error("An error occurred while READING '" + FILENAME + "' file.");
            return Optional.empty();
        }
        return FileUtils.convertToObj(UserStateImpl.class, rawState.get());
    }
}
