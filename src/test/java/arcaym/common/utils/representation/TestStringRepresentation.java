package arcaym.common.utils.representation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import arcaym.common.utils.representation.StringRepresentationAnnotatedObjectsProvider.TypeRepresentationObject;

class TestStringRepresentation {

    private static final double TEST_NUMBER = 1.1;
    private static final String TEST_STRING = "test string";
    private static final char TEST_CHAR = 'a';

    @ParameterizedTest
    @ArgumentsSource(StringRepresentationAnnotatedObjectsProvider.class)
    void testOfAnnotatedObjects(final TypeRepresentationObject object) {
        assertEquals(object.expectedRepresentation(), StringRepresentation.ofObject(object));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(booleans = { true, false })
    @ValueSource(bytes = { (byte) TEST_NUMBER })
    @ValueSource(chars = { TEST_CHAR })
    @ValueSource(doubles = { TEST_NUMBER })
    @ValueSource(floats = { (float) TEST_NUMBER })
    @ValueSource(ints = { (int) TEST_NUMBER })
    @ValueSource(longs = { (long) TEST_NUMBER })
    @ValueSource(shorts = { (short) TEST_NUMBER })
    @ValueSource(strings = { TEST_STRING, "" })
    @ValueSource(classes = { Object.class })
    void testOfNormalObjects(final Object object) {
        assertEquals(String.valueOf(object), StringRepresentation.ofObject(object));
    }

}
