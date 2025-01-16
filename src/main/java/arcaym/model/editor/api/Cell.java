package arcaym.model.editor.api;

import java.util.Collection;

import arcaym.model.game.objects.api.GameObjectType;

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
    Collection<GameObjectType> getValues();
}
