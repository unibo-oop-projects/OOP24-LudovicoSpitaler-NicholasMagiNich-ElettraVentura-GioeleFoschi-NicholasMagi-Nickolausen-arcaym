package arcaym.model.editor.impl;

import java.util.Collection;

import arcaym.common.utils.Position;
import arcaym.model.editor.ConstraintFailedException;
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
        return positions -> {
            if (!positions
            .stream()
            .allMatch(p1 -> 
                positions
                    .stream()
                    .anyMatch(p2 -> adjacencyCondition(p1, p2) || positions.size() == 1))) {
                throw new ConstraintFailedException("All the cells placed must be nearby");
            }
        };
    }

    private boolean adjacencyCondition(final Position p1, final Position p2) {
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

            @Override
            public void checkConstraint(final Collection<Position> currentMapSituation) throws ConstraintFailedException {
                if (currentMapSituation.size() > maxBlocks) {
                    throw new ConstraintFailedException("Maximum amount of object placed exceeded: Max = "
                    + maxBlocks 
                    + ", Placed = "
                    + currentMapSituation.size());
                }
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint minNumberOfBlocks(final int minBlocks) {
        return new MapConstraint() {

            @Override
            public void checkConstraint(final Collection<Position> currentMapSituation) throws ConstraintFailedException {
                if (currentMapSituation.size() < minBlocks) {
                    throw new ConstraintFailedException("The level does not have enough of object: ");
                }
            }

        };
    }

}
