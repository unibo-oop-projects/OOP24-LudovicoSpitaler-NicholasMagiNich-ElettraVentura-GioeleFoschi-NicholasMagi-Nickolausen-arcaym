package arcaym.testutils;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

/**
 * Utility class for {@link Arguments}.
 */
public final class ArgumentsUtils {

    private ArgumentsUtils() { }

    /**
     * Create an arguments stream with all combinations from the lists of arguments.
     * 
     * @param sources lists of arguments
     * @return stream of all arguments combinations
     */
    public static Stream<? extends Arguments> allCombinations(final List<?>... sources) {
        return Stream.of(sources)
            .map(l -> l.stream().map(List::of).toList())
            .reduce(
                (l1, l2) -> l1.stream().flatMap(
                    x -> l2.stream().map(y -> Stream.concat(x.stream(), y.stream()).toList())
                ).toList()
            )
            .map(l -> l.stream().map(x -> Arguments.of(x.toArray())))
            .orElse(Stream.empty());
    }

}
