package arcaym.model.editor.api;

import java.util.Collection;

import arcaym.common.point.api.Point;

/**
 * An interface modeling map constraints.
 * Examples:
 * - There can only be one player in the grid.
 * - All the goal tiles should be adjacent.
 * - There is a maximum amount of collectable in the level.
 */
public interface MapContsraint {
    /**
     * 
     * @param currentMapSituation
     * @return True if the constraint is respected 
     */
    boolean checkConstraint(Collection<Point> currentMapSituation); 
}
