package arcaym.common.utils.representation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for representing a class as string.
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeRepresentation {

    /**
     * Default {@link #open()} value.
     */
    String DEFAULT_OPEN = "[";

    /**
     * Default {@link #close()} value.
     */
    String DEFAULT_CLOSE = "]";

    /**
     * Default {@link #separator()} value.
     */
    String DEFAULT_SEPARATOR = ", ";

    /**
     * Default {@link #association()} value.
     */
    String DEFAULT_ASSOCIATION = "=";

    /**
     * String to open the fields section.
     * 
     * @return string value
     */
    String open() default DEFAULT_OPEN;

    /**
     * String to close the fields section.
     * 
     * @return string value
     */
    String close() default DEFAULT_CLOSE;

    /**
     * String to separate the fields.
     * 
     * @return string value
     */
    String separator() default DEFAULT_SEPARATOR;

    /**
     * String to associate a field to its value.
     * 
     * @return string value
     */
    String association() default DEFAULT_ASSOCIATION;

}
