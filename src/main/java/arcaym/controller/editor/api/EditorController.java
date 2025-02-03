package arcaym.controller.editor.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import arcaym.common.utils.Position;
import arcaym.controller.app.api.Controller;
import arcaym.model.editor.EditorGridException;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for an editor controller.
 */
public interface EditorController extends Controller { // Copy grid controller in here 

    /**
     * Builds the level that u been cooking.
     */
    void play();

    /**
     * 
     * @return the set of objects owned by the user that can be used in the editor.
     */
    Set<GameObjectType> getOwnedObjects();

    /**
     * Updates the selected object.
     * This will be the object placed in the grid in the next {@link #applyChange(Collection)}
     * @param object game object type
     */
    void setSelectedObject(GameObjectType object);

    /**
     * Restores the previeus state of the editor.
     */
    void undo();

    /**
     * Weather the editor is in the correct state for an undo.
     * @return True if an undo can be performed
     */
    boolean canUndo();

    /**
     * Ereases every object in the selected position.
     * 
     * @param positions the selected positions
     * @throws EditorGridException if a constraint is not respected
     */
    void eraseArea(Collection<Position> positions) throws EditorGridException;

    /**
     * Adds the last selected object from {@link #setSelectedObject(GameObjectType)} and
     * modifies the grid accordingly by adding / replacing objects inside.
     * 
     * @param positions The collection of position to change
     * @throws EditorGridException if a constraint is not respected
     */
    void applyChange(Collection<Position> positions) throws EditorGridException;

    /**
     * Saves the current state of the level.
     * @return True if the save was successful, false otherwise
     */
    boolean saveLevel();

    /**
     * This is to set the method to call when the view needs to be updated.
     * @param listener The fucntion to call
     */
    void setView(Consumer<Map<Position, List<GameObjectType>>> listener);

    /**
     * Signals the view that it needs to update, based on recent model changes.
     * @param map How the view has to change: Position p -> render the objects in the list
     */
    void updateView(Map<Position, List<GameObjectType>> map);
}
