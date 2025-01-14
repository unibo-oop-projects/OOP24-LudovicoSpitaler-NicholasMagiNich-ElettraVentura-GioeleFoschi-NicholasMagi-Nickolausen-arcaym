package arcaym.controller.editor.api;

import arcaym.model.editor.api.Memento;

/**
 * Keeps track of all the previeus state of the old {@link arcaym.model.editor.api.Grid} states.
 */
public interface MementoCaretaker {


    /**
     * Gets the previeus {@link arcaym.model.editor.api.Grid} state based on the current index.
     * 
     * @return The state to restore.
     */
    Memento getPrevieus();

    /**
     * Gets the next {@link arcaym.model.editor.api.Grid} state based on the
     * current index.
     * 
     * @return The state to restore.
     */
    Memento getNext();

    /**
     * Adds the given {@link Memento} to the history.
     * If the current index is not the last index, deltes all the states following the index, adds 
     * the new one and restores the index to point to @param m
     * @param m The last state added
     */
    void addMemento(Memento m);

    /**
     * Checks if there is a previeus state to return.
     * 
     * @return True if there exist a previeus state to restore
     */
    boolean hasPrevieus();

    /**
     * Checks if there is a "future" state to return.
     * 
     * @return True if the current index is not poiting to the last state.
     */
    boolean hasNext();
}
