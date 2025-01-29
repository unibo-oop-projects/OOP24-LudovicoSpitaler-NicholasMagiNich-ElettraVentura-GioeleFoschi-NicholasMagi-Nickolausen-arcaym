package arcaym.controller.editor.api;

import java.util.Collection;

import arcaym.common.utils.Position;

/**
 * Keeps track of all the previeus state of the old {@link arcaym.model.editor.api.Grid} states.
 */
public interface MementoCaretaker {
    /**
     * Saves the state of the positions given.
     * @param positions the positions to save.
     */
    void takeScreenShot(Collection<Position> positions);

    /**
     * Recovers the last state of the grid.
     */
    void recoverLastState();
}

