package arcaym.common.point;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.common.point.impl.BasePoint;
import arcaym.common.point.api.Point;

class PointsProvider implements ArgumentsProvider {

    interface PointConstructor extends BiFunction<Integer, Integer, Point> { }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        final Stream<PointConstructor> implementations = Stream.of(
            BasePoint::new
        );
        return implementations.map(Arguments::of);
    }

}
