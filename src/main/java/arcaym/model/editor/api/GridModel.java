package arcaym.model.editor.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorGridException;
import arcaym.model.game.objects.GameObjectType;

/**
 * The business logic of the grid.
 */
public interface GridModel {

    /**
     * Restores the previeus state of the grid.
     */
    void undo();

    /**
     * Tells if the editor is in the correct state for an undo.
     * 
     * @return True if an redo can be performed
     */
    boolean canUndo();

    /**
     * Places the object type in the positions given, if the placements does not break any constraints.
     * 
     * @param positions The position on wich the objects will be placed
     * @param type The object to place
     * @throws EditorGridException if the placement is not allowed
     */
    void placeObjects(Collection<Position> positions, GameObjectType type) throws EditorGridException;

    /**
     * Removes the objects in the positions given, if the removal does not break any
     * constraints.
     * 
     * @param positions The positions to erase.
     * @throws EditorGridException if the removal is not allowed
     */
    void removeObjects(Collection<Position> positions) throws EditorGridException;

    /**
     * Used to signal to the controller that the grid has changed its internal state.
     * @return A data structure representing the state change.
     */
    Map<Position, List<GameObjectType>> getUpdatedGrid();

    /**
     * Returns a map representing the whole map.
     * @return A map where to each position corresponds a list of game objects in that position
     */
    Map<Position, List<GameObjectType>> getFullMap();

    /**
     * Checks the "before playing" constraints.
     * 
     * @throws EditorGridException if any constraint check is failed
     */
    void beforeStartCheck() throws EditorGridException;

    /**
     * Saves the current state of the grid in a file for later use.
     * @param uuid The name of the file.
     * @return Returns true if the file was saved successfully
     */
    boolean saveState(String uuid);
}
