package arcaym.model.game.core.events.api;

/**
 * Interface for a basic event.
 */
public interface Event {

    /**
     * Default {@link #priority()} value.
     */
    int NO_PRIORITY = Integer.MAX_VALUE;

    /**
     * Get priority value.
     * 
     * @return priority
     */
    default int priority() {
        return NO_PRIORITY;
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
