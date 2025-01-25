package arcaym.model.game.events.api;

/**
 * User input events.
 */
public enum InputEvent implements Event {

    /**
     * Left input.
     */
    LEFT(false),

    /**
     * Right input.
     */
    RIGHT(false),

    /**
     * Up input.
     */
    UP(false),

    /**
     * Down input.
     */
    DOWN(false);

    private final boolean isActive;

    InputEvent(final boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 
     * @return if input event is active or not.
     */
    public boolean isActive() {
        return this.isActive;
    }

}
