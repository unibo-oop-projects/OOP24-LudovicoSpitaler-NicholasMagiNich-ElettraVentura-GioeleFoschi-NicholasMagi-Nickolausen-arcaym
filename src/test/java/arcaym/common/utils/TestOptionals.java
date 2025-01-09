package arcaym.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class TestOptionals {

    @Test
    void testOrIllegalState() {
        assertEquals(0, Optionals.orIllegalState(Optional.of(0), ""));

        final var errorMessage = "Test error";
        final var exception = assertThrowsExactly(
            IllegalStateException.class, 
            () -> Optionals.orIllegalState(Optional.empty(), errorMessage)
        );
        assertEquals(errorMessage, exception.getMessage());
    }

}
