package arcaym.model.game.core.score;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.model.game.core.score.impl.BaseGameScoreFactory;

class GameScoreFactoriesProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(
            Arguments.of(new BaseGameScoreFactory())
        );
    }

}
