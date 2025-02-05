package arcaym.common.utils.representation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Utility class to represent {@link TypeRepresentation}-annotated objects as strings.
 * 
 * <p>
 * Example of usage:
 * </p>
 * <pre>
 * {@code @TypeRepresentation}
 * public class TestClass {
 *      private int value = 5;
 *      ...
 *      {@code @FieldRepresentation}
 *      int getValue() {
 *          return this.value;
 *      }
 *      ...
 *      {@code @Override}
 *      String toString() {
 *          return StringRepresentation.ofObject(this);
 *      }
 * }
 * 
 * System.out.println(new TestClass()); // TestClass[getValue=5]
 * </pre>
 * 
 * @see TypeRepresentation
 * @see FieldRepresentation
 */
public final class StringRepresentation {

    private StringRepresentation() { }

    /**
     * Get string representation of object.
     * If the object class is annotated as {@link TypeRepresentation}, represent accordingly.
     * Otherwise, use {@link String#valueOf(Object)}.
     * 
     * @param object object to represent
     * @return string representation
     */
    public static String ofObject(final Object object) {
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
        final var fieldsRepresentations = Stream.of(objectClass.getMethods())
            .filter(m -> m.isAnnotationPresent(FieldRepresentation.class))
            .map(m -> ofMethod(m, object, typeAnnotation))
            .sorted()
            .toList();

        return builder.append(String.join(typeAnnotation.separator(), fieldsRepresentations))
            .append(typeAnnotation.close())
            .toString();
    }

    private static String ofMethod(
        final Method method,
        final Object object,
        final TypeRepresentation typeAnnotation
    ) {
        if (method.getParameterCount() != 0) {
            throw new IllegalArgumentException(
                new StringBuilder("Method ")
                    .append(method.getName())
                    .append(" is annotated as field but has parameters")
                    .toString()
            );
        }

        return new StringBuilder(method.getName())
            .append(typeAnnotation.association())
            .append(ofObject(invokeMethod(method, object)))
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
