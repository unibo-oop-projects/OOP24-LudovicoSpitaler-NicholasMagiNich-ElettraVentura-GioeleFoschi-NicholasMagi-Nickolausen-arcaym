package arcaym.model.editor.constraints;

import java.util.Collection;

import arcaym.common.utils.Position;
import arcaym.model.editor.ConstraintFailedException;

/**
 * An interface modeling map constraints.
 * Examples:
 * - There can only be one player in the grid.
 * - All the goal tiles should be adjacent.
 * - There is a maximum amount of collectable in the level.
 */
@FunctionalInterface
public interface MapConstraint {
    /**
     * Checks that the constraint is respected.
     * @param currentMapSituation the map of one specific object
     * @throws ConstraintFailedException if the constraint is not respected
     */
    void checkConstraint(Collection<Position> currentMapSituation) throws ConstraintFailedException; 
}
