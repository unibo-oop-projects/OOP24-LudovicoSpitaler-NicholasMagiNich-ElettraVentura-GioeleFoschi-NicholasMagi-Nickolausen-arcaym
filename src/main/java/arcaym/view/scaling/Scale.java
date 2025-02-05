package arcaym.view.scaling;

import java.util.Objects;

/**
 * Enum of possible window scales.
 */
public enum Scale {

    /**
     * Scale to fill all screen.
     */
    FULLSCREEN(1f, "Fullscreen"),

    /**
     * Scale to fill 75% of the screen.
     */
    X75(.75f),

    /**
     * Scale to fill 50% of the screen.
     */
    X50(.50f);

    private final float value;
    private final String label;

    Scale(final float value, final String label) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Scale value must be between 0 and 1");
        }
        this.value = value;
        this.label = Objects.requireNonNull(label);
    }

    Scale(final float value) {
        this(value, String.valueOf(value));
    }

    /**
     * @return scale factor
     */
    public float value() {
        return this.value;
    }

    /**
     * @return scale label
     */
    public String label() {
        return this.label;
    }

}
