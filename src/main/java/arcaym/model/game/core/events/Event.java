package arcaym.model.game.core.events;

/**
 * Interface for a basic event.
 */
public interface Event {

    /**
     * Default {@link #priority()} value.
     */
    int NO_PRIORITY = Integer.MAX_VALUE;


    /**
     * Default {@link #isTerminal()} value.
     */
    boolean DEFAULT_TERMINAL = false;

    /**
     * @return priority value
     */
    default int priority() {
        return NO_PRIORITY;
    }

    /**
     * @return if the event should clear all the other events
     */
    default boolean isTerminal() {
        return DEFAULT_TERMINAL;
    }

    /**
     * Compare two events based on {@link #priority()}.
     * A lower value means higher priority.
     * 
     * @param e1 first event
     * @param e2 second event
     * @return comparison result
     */
    static int compare(final Event e1, final Event e2) {
        return Integer.compare(e1.priority(), e2.priority());
    }

}
