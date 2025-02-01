package arcaym.model.editor.api;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorGridException;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * The interface used by {@link arcaym.controller.editor.api.GridController} that manages the editor grid.
 */
public interface Grid {

    /**
     * Sets the object @param type in all the positions in the collection.
     * 
     * @param positions The collection of position of the grid
     * @param type The type of object to be placed
     * @throws EditorGridException when the positions given do not respect grid rules
     */
    void setObjects(Collection<Position> positions, GameObjectType type) throws EditorGridException;

    /**
     * Removes every objects from the given positions.
     * Also performs all the checks of the present constraint to see if they are still respected
     * 
     * @param positions
     * @throws EditorGridException when removing the positiongiven puts the grid in a inconsistent state
     */
    void removeObjects(Collection<Position> positions) throws EditorGridException;

    /**
     * Returns a set of {@link GameObjectType} that represent every object contained in @param pos .
     * 
     * @param pos The position of which to get the objects
     * @return A list of {@link GameObjectType} ordered by priority of render:
     * - block first, entity second, collectable third
     */
    List<GameObjectType> getObjects(Position pos);

    /**
     * Saves only the cells modified.
     * 
     * @param positions The cells that needs to be saved
     * @return A {@link Memento} object representing the state
     */
    Memento takeSnapshot(Collection<Position> positions);

    /**
     * Recovers a specific state.
     * 
     * @param state The state to recover
     * @return Returns the set of positions that were modified by the operation
     */
    Set<Position> recoverSavedState(Memento state);

    /**
     * Saves the current state of the grid.
     * 
     * @param uuid The name of the file to save.
     * @return True if the save was succesfull
     */
    boolean saveState(String uuid);
}
