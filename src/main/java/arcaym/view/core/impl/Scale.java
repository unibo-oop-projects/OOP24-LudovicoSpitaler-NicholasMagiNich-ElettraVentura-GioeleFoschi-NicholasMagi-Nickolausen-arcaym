package arcaym.view.core.impl;

import java.util.Objects;

/**
 * Enum of possible window scales.
 */
public enum Scale {

    /**
     * Scale to occupy all screen.
     */
    FULLSCREEN(1f, "Fullscreen"),

    /**
     * Scale to occupy 75% of the screen.
     */
    X75(.75f),

    /**
     * Scale to occupy 50% of the screen.
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
     * Get scale value.
     * 
     * @return scale factor
     */
    public float value() {
        return this.value;
    }

    /**
     * Get scale label.
     * 
     * @return scale label
     */
    public String label() {
        return this.label;
    }

    /**
     * Get if the scaled window should be fullscreen.
     * 
     * @return if the window should be fullscreen
     */
    public boolean isFullScreen() {
        return this.value == 1;
    }

}
