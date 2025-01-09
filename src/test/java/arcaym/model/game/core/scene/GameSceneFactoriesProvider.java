package arcaym.model.game.core.scene;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.model.game.core.scene.impl.BaseGameSceneFactory;

class GameSceneFactoriesProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of(new BaseGameSceneFactory())
        );
    }

}
