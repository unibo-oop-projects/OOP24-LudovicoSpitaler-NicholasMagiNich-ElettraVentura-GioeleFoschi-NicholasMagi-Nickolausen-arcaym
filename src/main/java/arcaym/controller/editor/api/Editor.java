package arcaym.controller.editor.api;

import java.util.Collection;
import java.util.List;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorGridException;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * The interface used to manage the editor side.
 */
public interface Editor {

    /**
     * Sets the list of available objects to place.
     * @param availableList the list of currently unlocked objects.
     */
    void updateListOfAvailableObjects(List<GameObjectType> availableList);

    /**
     * Gets the list of currently available objects.
     * @return A list of objects
     */
    List<GameObjectType> getListOfAvailableObjects();

    /**
     * Updates the selected object.
     * This will be the object placed in the grid in the next {@link #applyChange()}
     * @param object
     */
    void setSelectedObject(GameObjectType object);

    /**
     * Restores the previeus state of the editor.
     */
    void undo();

    /**
     * If an {@link #undo()} was executed, this can revert that action.
     */
    void redo();

    /**
     * Weather the editor is in the correct state for an undo.
     * @return True if an undo can be performed
     */
    boolean canUndo();

    /**
     * Weather the editor is in the correct state for an redo.
     * 
     * @return True if an redo can be performed
     */
    boolean canRedo();

    /**
     * Ereases every object in the selected position.
     * 
     * @param positions the selected positions
     */
    void eraseArea(Collection<Position> positions) throws EditorGridException;

    /**
     * Adds the last selected object from {@link #setSelectedObject(GameObjectType)} and
     * modifies the grid accordingly by adding / replacing objects inside.
     * 
     * @param positions The collection of position to change
     */
    void applyChange(Collection<Position> positions) throws EditorGridException;
}
