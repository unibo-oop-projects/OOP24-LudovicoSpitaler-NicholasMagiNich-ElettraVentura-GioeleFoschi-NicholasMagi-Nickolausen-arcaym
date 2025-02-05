package arcaym.model.editor.grid;

import java.util.List;

import arcaym.model.game.objects.GameObjectType;

/**
 * The interface for a single cell of the {@link Grid}.
 */
public interface Cell {
    /**
     * Sets the internal value of the cell.
     * Cell can have multiple values
     * Examples
     * - An enemy over a floor
     * - A collectable over an enemy over a wall
     * @param type the to add to the values
     */
    void setValue(GameObjectType type);

    /**
     * Gets the collection of values in the cell.
     * @return Returns the list of objects contained in that cell
     */
    List<GameObjectType> getValues();

    /**
     * Used to get a copy of the cell.
     * @return An exact copy of the cell
     */
    Cell getCopy();
}
