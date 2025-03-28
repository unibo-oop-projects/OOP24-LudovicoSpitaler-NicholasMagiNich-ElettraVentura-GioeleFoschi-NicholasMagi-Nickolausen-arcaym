package arcaym.model.editor.grid;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.undo.Memento;
import arcaym.model.game.objects.GameObjectType;

/**
 * The interface used by {@link arcaym.controller.editor.api.GridController} that manages the editor grid.
 */
public interface Grid {

    /**
     * Getter for the grid size.
     * 
     * @return The size of the grid:
     *         <br>
     *         - {@link Position#x()} = width;
     *         <br>
     *         - {@link Position#y()} = height
     */
    Position getSize();

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
     * @param positions positions to remove
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
     * Checks the "before playing" constraints.
     * 
     * @throws EditorGridException if any constraint check is failed
     */
    void canPlay() throws EditorGridException;

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
