package arcaym.model.editor.impl;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.model.editor.api.MapConstraint;
import arcaym.model.editor.api.MapConstraintsFactory;

/**
 * A concrete factory of map constraints.
 */
public class MapConstraintFactoryImpl implements MapConstraintsFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint adjacencyConstraint() {
        return positions -> 
            positions
            .stream()
            .allMatch(p1 -> 
                positions
                    .stream()
                    .anyMatch(p2 -> adjacencyCondition(p1, p2) || positions.size() == 1));

    }

    private boolean adjacencyCondition(final Point p1, final Point p2) {
        return (p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y()) == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint singleBlockConstraint() {
        return maxNumberOfBlocks(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint maxNumberOfBlocks(final int maxBlocks) {
        return new MapConstraint() {

            private final int maxOfType = maxBlocks;

            @Override
            public boolean checkConstraint(final Collection<Point> currentMapSituation) {
                return currentMapSituation.size() <= this.maxOfType;
            }
            
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint minNumberOfBlocks(final int minBlocks) {
        return new MapConstraint() {

            private final int minOfType = minBlocks;

            @Override
            public boolean checkConstraint(final Collection<Point> currentMapSituation) {
                return currentMapSituation.size() <= this.minOfType;
            }

        };
    }

}
