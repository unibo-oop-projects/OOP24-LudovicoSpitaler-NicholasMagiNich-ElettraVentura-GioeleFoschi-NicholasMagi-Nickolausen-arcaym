package arcaym.model.editor.api;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * The interface used by {@link arcaym.controller.editor.api.Editor} that manages the editor grid.
 */
public interface Grid {

    /**
     * Takes a snapshot of the current state of the grid for undo / redo 
     * implementation.
     * Parameter are needed to store as little information as possible.
     * 
     * @param positions The collection of position that will be changing.
     * @param changingTo The category the position will be changing to.
     * @return The current state of the Grid without breaking encapsulation.
     */
    Memento takeSnapshot(Collection<Point> positions, GameObjectCategory changingTo);

    /**
     * Restores the state described by the memento object.
     * @param state
     */
    void restore(Memento state);

    /**
     * Sets the object @param type in all the positions in the collection.
     * @param positions The collection of position of the grid
     * @param type The type of object to be placed
     */
    void setObjects(Collection<Point> positions, GameObjectType type);

    /**
     * At any given time there can only be a maximum of 1
     * {@link arcaym.model.game.core.objects.api.GameObjectCategory#PLAYER}.
     * 
     * @param positions The incoming collection of objects, to be added to the grid.
     * @return True if the constraint is respected
     */
    default boolean playerConstraint(Collection<Point> positions) {
        return false;
    }

    /**
     * At any given time there can only be one cluster of
     * {@link arcaym.model.game.core.objects.api.GameObjectCategory#GOAL}.
     * A cluster is a set of cells all directly connected (not diagonally)
     * 
     * @param positions The incoming collection of objects, to be added to the grid.
     * @return True if the constraint is respected
     */
    default boolean goalConstraint(Collection<Point> positions) {
        return false;
    }
}
