package arcaym.controller.game.core.events;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.controller.game.core.events.impl.ThreadSafeEventsManager;
import arcaym.testutils.ArgumentsUtils;

class EventsManagersProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return ArgumentsUtils.allCombinations(List.of(
            new ThreadSafeEventsManager<>()
        ));
    }

}
