package arcaym.model.editor.api;

/**
 * The memento interface, used to store a state, and restore it in a second moment.
 */
public interface Memento {
    /**
     * Restores the saved state.
     */
    void restore();
}
