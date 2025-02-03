package arcaym.common.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class for {@link Optional}.
 */
public final class Optionals {

    private Optionals() { }

    /**
     * Get value of optional or throw exception if empty.
     * @see Optional#orElseThrow(Supplier)
     * 
     * @param <T> type of value inside optional
     * @param value optional to extract
     * @param message exception message
     * @return the value inside the optional
     * @throws IllegalStateException if optional value is empty
     */
    public static <T> T orIllegalState(final Optional<T> value, final String message) {
        return value.orElseThrow(() -> new IllegalStateException(Objects.requireNonNull(message)));
    }
}
