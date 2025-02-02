package arcaym.controller.user.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import arcaym.common.utils.file.FileUtils;
import arcaym.controller.user.api.UserStateSerializer;
import arcaym.model.user.api.UserStateInfo;

/**
 * Implementation of {@link UserStateSerializer}. 
 */
public class UserStateSerializerImpl implements UserStateSerializer {

    private static final String EXTENSION = ".json";
    private static final String FILENAME = "user_data";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStateSerializer.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final UserStateInfo userState) {
        return this.save(userState, FILENAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final UserStateInfo userState, final String fileName) {
        validateFileName(fileName);
        try {
            Files.writeString(
                getPathOf(fileName),
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
    public Optional<UserStateInfo> load() {
        return this.load(FILENAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserStateInfo> load(final String fileName) {
        validateFileName(fileName);
        final var rawState = FileUtils.readFromPath(getPathOf(fileName));
        if (rawState.isEmpty()) {
            LOGGER.error("An error occurred while READING '" + FILENAME + "' file.");
            return Optional.empty();
        }
        return FileUtils.convertToObj(UserStateInfo.class, rawState.get());
    }

    private Path getPathOf(final String fileName) {
        return Path.of(FileUtils.SAVES_FOLDER, fileName.concat(EXTENSION));
    }

    private void validateFileName(final String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank() || fileName.isEmpty()) {
            throw new IllegalArgumentException("Invalid filename!");
        }
    }
}
