package arcaym.testutils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

class TestArgumentsUtils {

    private static List<List<Object>> argumentsList(final Stream<? extends Arguments> arguments) {
        return arguments.map(Arguments::get).map(List::of).toList();
    }

    private static void assertArgumentsEquals(
        final Stream<? extends Arguments> expected, 
        final Stream<? extends Arguments> arguments
    ) {
        assertEquals(argumentsList(expected), argumentsList(arguments));
    }

    @Test
    void testAllCombinations() {
        assertArgumentsEquals(Stream.empty(), ArgumentsUtils.allCombinations());
        assertArgumentsEquals(
            Stream.of(Arguments.of(1), Arguments.of(2), Arguments.of(3)), 
            ArgumentsUtils.allCombinations(List.of(1, 2, 3))
        );
        assertArgumentsEquals(
            Stream.of(
                Arguments.of(1, "a"),
                Arguments.of(1, "b"),
                Arguments.of(2, "a"),
                Arguments.of(2, "b"),
                Arguments.of(3, "a"),
                Arguments.of(3, "b")
            ),
            ArgumentsUtils.allCombinations(
                List.of(1, 2, 3),
                List.of("a", "b")
            )
        );
        assertArgumentsEquals(
            Stream.of(
                Arguments.of(1, "a", true),
                Arguments.of(1, "a", false),
                Arguments.of(1, "b", true),
                Arguments.of(1, "b", false),
                Arguments.of(2, "a", true),
                Arguments.of(2, "a", false),
                Arguments.of(2, "b", true),
                Arguments.of(2, "b", false),
                Arguments.of(3, "a", true),
                Arguments.of(3, "a", false),
                Arguments.of(3, "b", true),
                Arguments.of(3, "b", false)
            ),
            ArgumentsUtils.allCombinations(
                List.of(1, 2, 3),
                List.of("a", "b"),
                List.of(true, false)
            )
        );
    }

}
