package arcaym.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class TestOptionals {

    private static final String ERROR_MESSAGE = "Test error";
    private static final int VALUE = 0;

    @Test
    void testOrIllegalState() {
        assertEquals(VALUE, Optionals.orIllegalState(Optional.of(VALUE), ERROR_MESSAGE));
        final var exception = assertThrowsExactly(
            IllegalStateException.class, 
            () -> Optionals.orIllegalState(Optional.empty(), ERROR_MESSAGE)
        );
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

}
