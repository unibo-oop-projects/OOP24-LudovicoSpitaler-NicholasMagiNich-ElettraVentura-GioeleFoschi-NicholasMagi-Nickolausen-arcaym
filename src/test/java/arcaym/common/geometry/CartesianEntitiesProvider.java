package arcaym.common.geometry;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

class CartesianEntitiesProvider implements ArgumentsProvider {

    interface CartesianEntityConstructor<T extends CartesianEntity<T>> extends BiFunction<Double, Double, T> {
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        final Stream<CartesianEntityConstructor<?>> implementations = Stream.of(
            (CartesianEntityConstructor<Point>) Point::of,
            (CartesianEntityConstructor<Vector>) Vector::of
        );
        return implementations.map(Arguments::of);
    }

}
