package arcaym.model.editor.api;

import java.util.Optional;

/**
 * Class used to save old states in form of a {@link Memento}.
 */
public interface MementoCaretaker {
    /**
     * Saves to the collection of previews states the given {@link Memento}.
     * 
     * @param state The state to save to the collection
     */
    void saveSnapshot(Memento state);

    /**
     * Recovers the latest snapshot saved.
     * If multiple calls are made consecutevly, recovers the second to last.
     * 
     * @return The {@link Memento} class consisting in the state to recover,
     * or an {@link Optional#empty()} if there is no state to recover
     */
    Optional<Memento> recoverSnapshot();

    /**
     * Checks if there are any old states to recover.
     * @return Returns true if the undo can be performed
     */
    boolean canUndo();
}
