package arcaym.model.game.core.objects;

import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.model.game.core.components.impl.UniqueComponentsBasedObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.objects.api.GameObjectType;

class GameObjectsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        final Stream<Function<GameObjectType, GameObject>> defaultConstructors = Stream.of(
            UniqueComponentsBasedObject::new
        );
        return defaultConstructors
            .flatMap(c -> Stream.of(GameObjectType.values()).map(c::apply))
            .map(Arguments::of);
    }

}
