package arcaym.model.editor.api;

import java.util.Collection;
import java.util.List;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorGridException;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * The interface used by {@link arcaym.controller.editor.api.Editor} that manages the editor grid.
 */
public interface Grid {

    /**
     * Adds a {@link MapConstraint} to the grid.
     * The constraint targets a {@link GameObjectType}
     * 
     * @param contsraint The constraint to apply.
     * @param target The GameObjectType to target
     */
    void setObjectConstraint(MapConstraint contsraint, GameObjectType target);

    /**
     * Adds a {@link MapConstraint} to the grid.
     * The constraint targets a {@link GameObjectCategory}
     * 
     * @param contsraint The constraint to apply.
     * @param target     The GameObjectCategory to target
     */
    void setCategoryConstraint(MapConstraint contsraint, GameObjectCategory target);

    /**
     * Sets the object @param type in all the positions in the collection.
     * 
     * @param positions The collection of position of the grid
     * @param type The type of object to be placed
     * @throws 
     */
    void setObjects(Collection<Position> positions, GameObjectType type) throws EditorGridException;

    /**
     * Removes every objects from the given positions.
     * @param positions
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
}
