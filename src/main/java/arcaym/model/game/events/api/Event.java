package arcaym.model.game.events.api;

/**
 * Interface for a basic event.
 */
public interface Event {

    /**
     * Default {@link Event#priority()} value.
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
     * Compare two events based on {@link Event#priority()}.
     * Lower value means higher priority.
     * 
     * @param e1 first event
     * @param e2 second event
     * @return comparison result
     */
    static int compare(final Event e1, final Event e2) {
        return Integer.compare(e1.priority(), e2.priority());
    }

}
