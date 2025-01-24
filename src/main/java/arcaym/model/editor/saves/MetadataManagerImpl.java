package arcaym.model.editor.saves;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import arcaym.common.utils.file.FileUtils;

/**
 * An implementation of MetadataManager that saves {@link LevelMetadata} objects into json files.
 */
public class MetadataManagerImpl implements MetadataManager {

    private static final String EXTENTION = ".json";
    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataManagerImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveMetadata(final LevelMetadata metadata) {
        FileUtils.createMetadataDirectory();
        try {
            Files.writeString(
                Path.of(FileUtils.METADATA_FOLDER, metadata.uuid().concat(EXTENTION)),
                new Gson().toJson(metadata),
                StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("An error occurred while writing metadata file.", e);
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LevelMetadata> loadData() {
        try (Stream<Path> paths = Files.walk(Paths.get(FileUtils.METADATA_FOLDER))) {
            return paths.
                filter(Files::isRegularFile)
                .map(this::readFromPath)
                .flatMap(Optional::stream)
                .map(this::convertToMetadata)
                .flatMap(Optional::stream)
                .toList();
        } catch (IOException e) {
            LOGGER.error("An error occurred while reading files", e);
        }
        // if an error occurs return an empty list
        return List.of();
    }

    private Optional<String> readFromPath(final Path path) {
        try {
            return Optional.of(Files.readString(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private Optional<LevelMetadata> convertToMetadata(final String content) {
        final Gson json = new Gson();
        try {
            return Optional.of(json.fromJson(content, LevelMetadata.class));
        } catch (JsonSyntaxException e) {
            return Optional.empty();
        }
    }

}
