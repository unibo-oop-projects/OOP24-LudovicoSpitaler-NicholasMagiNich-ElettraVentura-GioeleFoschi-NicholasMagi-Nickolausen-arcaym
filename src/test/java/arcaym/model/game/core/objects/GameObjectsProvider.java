package arcaym.model.game.core.objects;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.model.game.core.components.impl.UniqueComponentsBasedObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.testutils.ArgumentsUtils;

class GameObjectsProvider implements ArgumentsProvider {

    interface GameObjectConstructor extends Function<GameObjectType, GameObject> { }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        final List<GameObjectConstructor> implementations = List.of(
            UniqueComponentsBasedObject::new
        );
        return ArgumentsUtils.allCombinations(implementations, List.of(GameObjectType.values()));
    }

}
