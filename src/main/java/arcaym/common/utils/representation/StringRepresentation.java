package arcaym.common.utils.representation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Utility class to represent objects as strings.
 */
public final class StringRepresentation {

    private StringRepresentation() { }

    /**
     * Represent object as string.
     * If the object class is annotated as {@link TypeRepresentation}, represent accordingly.
     * Else, use {@link String#valueOf(Object)}.
     * 
     * @param object object to represent
     * @return string representation
     */
    public static String toString(final Object object) {
        if (object == null) {
            return String.valueOf((Object) null); // cast to ensure overload for objects is used
        }
        final var objectClass = object.getClass();
        if (!objectClass.isAnnotationPresent(TypeRepresentation.class)) {
            return String.valueOf(object);
        }

        final var builder = new StringBuilder(objectClass.getSimpleName());
        final var typeAnnotation = objectClass.getAnnotation(TypeRepresentation.class);
        builder.append(typeAnnotation.open());
        final var methodsRepresentations = List.of(objectClass.getMethods()).stream()
            .filter(m -> m.isAnnotationPresent(FieldRepresentation.class))
            .map(m -> methodString(typeAnnotation, m, object)).toList();

        builder.append(String.join(typeAnnotation.separator(), methodsRepresentations));
        return builder.toString();
    }

    private static String methodString(
        final TypeRepresentation typeAnnotation, 
        final Method fieldMethod,
        final Object object
    ) {
        if (fieldMethod.getParameterCount() != 0) {
            throw new IllegalArgumentException(
                new StringBuilder("Method ")
                .append(fieldMethod.getName())
                .append(" is annotated as field but has parameters")
                .toString()
            );
        }

        final var methodResult = invokeMethod(fieldMethod, object);
        return new StringBuilder(fieldMethod.getName())
            .append(typeAnnotation.association())
            .append(toString(methodResult))
            .toString();
    }

    private static Object invokeMethod(
        final Method method, 
        final Object object, 
        final Object... args
    ) {
        try {
            return Objects.requireNonNull(method).invoke(Objects.requireNonNull(object), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException(
                new StringBuilder("Method ")
                    .append(method.getName())
                    .append("invocation raised ")
                    .append(e)
                    .toString()
            );
        }
    }

}
