package arcaym.controller.editor.api;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * The interface used to manage the editor side.
 */
public interface Editor {
    
    /**
     * Updates the selected object.
     * This will be the object placed in the grid in the next {@link #applyChange()}
     * @param object
     */
    void setSelectedObject(GameObjectType object);

    /**
     * Restores the previews state of the editor.
     */
    void undo();

    /**
     * If an undo was previewsly executed, this can revert that action.
     */
    void redo();

    /**
     * Weather the editor is in the correct state for an undo
     * @return True if an undo can be performed
     */
    boolean canUndo();

    /**
     * Weather the editor is in the correct state for an redo
     * 
     * @return True if an redo can be performed
     */
    boolean canRedo();

    /**
     * Adds the last selected object from {@link #setSelectedObject(GameObjectType)} and
     * modifies the grid accordingly by adding / replacing objects inside
     */
    void applyChange(Collection<Point> positions);
}
