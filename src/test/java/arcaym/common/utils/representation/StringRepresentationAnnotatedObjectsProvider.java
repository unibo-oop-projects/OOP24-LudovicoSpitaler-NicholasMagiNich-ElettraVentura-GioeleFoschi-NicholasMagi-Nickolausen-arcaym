package arcaym.common.utils.representation;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import arcaym.testutils.ArgumentsUtils;

class StringRepresentationAnnotatedObjectsProvider implements ArgumentsProvider {

    interface TypeRepresentationObject {

        String expectedRepresentation();

    }

    @TypeRepresentation
    static class MainObject implements TypeRepresentationObject {

        private static final String STRING = "Test field";
        private static final int INT = 0;
        private static final List<Integer> LIST = List.of(1, 2, 3);
        private static final SubObject OBJECT = new SubObject();

        @TypeRepresentation
        static class SubObject implements TypeRepresentationObject {

            private static final boolean BOOLEAN = false;

            @FieldRepresentation
            public boolean isBoolean() {
                return BOOLEAN;
            }

            @Override
            public String expectedRepresentation() {
                return new StringBuilder(getClass().getSimpleName())
                    .append(TypeRepresentation.DEFAULT_OPEN)
                    .append("isBoolean").append(TypeRepresentation.DEFAULT_ASSOCIATION).append(BOOLEAN)
                    .append(TypeRepresentation.DEFAULT_CLOSE)
                    .toString();
            }

        }

        @FieldRepresentation
        public String getString() {
            return STRING;
        }

        @FieldRepresentation
        public int getInt() {
            return INT;
        }

        @FieldRepresentation
        public List<Integer> getList() {
            return LIST;
        }

        @FieldRepresentation
        public SubObject getObject() {
            return OBJECT;
        }

        @Override
        public String expectedRepresentation() {
            return new StringBuilder(getClass().getSimpleName())
                .append(TypeRepresentation.DEFAULT_OPEN)
                .append("getInt").append(TypeRepresentation.DEFAULT_ASSOCIATION).append(INT)
                .append(TypeRepresentation.DEFAULT_SEPARATOR)
                .append("getList").append(TypeRepresentation.DEFAULT_ASSOCIATION).append(LIST)
                .append(TypeRepresentation.DEFAULT_SEPARATOR)
                .append("getObject").append(TypeRepresentation.DEFAULT_ASSOCIATION).append(OBJECT.expectedRepresentation())
                .append(TypeRepresentation.DEFAULT_SEPARATOR)
                .append("getString").append(TypeRepresentation.DEFAULT_ASSOCIATION).append(STRING)
                .append(TypeRepresentation.DEFAULT_CLOSE)
                .toString();
        }

    }

    @TypeRepresentation(
        open = DifferentRepresentationObject.OPEN,
        close = DifferentRepresentationObject.CLOSE,
        association = DifferentRepresentationObject.ASSOCIATION,
        separator = DifferentRepresentationObject.SEPARATOR
    )
    static class DifferentRepresentationObject implements TypeRepresentationObject {

        private static final String OPEN = "(";
        private static final String CLOSE = ")";
        private static final String ASSOCIATION = "->";
        private static final String SEPARATOR = ";";

        private static final String STRING = "Test field";
        private static final int INT = 0;

        @FieldRepresentation
        public String getString() {
            return STRING;
        }

        @FieldRepresentation
        public int getInt() {
            return INT;
        }

        @Override
        public String expectedRepresentation() {
            return new StringBuilder(getClass().getSimpleName())
                .append(OPEN)
                .append("getInt").append(ASSOCIATION).append(INT)
                .append(SEPARATOR)
                .append("getString").append(ASSOCIATION).append(STRING)
                .append(CLOSE)
                .toString();
        }

    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return ArgumentsUtils.allCombinations(List.of(
            new MainObject(),
            new DifferentRepresentationObject()
        ));
    }

}
