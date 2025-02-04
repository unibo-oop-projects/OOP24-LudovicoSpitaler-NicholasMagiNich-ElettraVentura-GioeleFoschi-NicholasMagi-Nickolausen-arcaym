package arcaym.model.editor.impl;

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
        // Only partially works:
        // Could not implement Connected-Component Labeling algorithm due to time constraints
        return positions -> {
            if (!positions
                    .stream()
                    .allMatch(p1 -> 
                        positions
                            .stream()
                            .anyMatch(p2 -> adjacencyCondition(p1, p2))) && positions.size() != 1) {
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
        return positions -> {
            if (positions.size() > maxBlocks) {
                throw new ConstraintFailedException("Maximum amount of object placed exceeded: Max = "
                + maxBlocks
                + ", Placed = "
                + positions.size());
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint minNumberOfBlocks(final int minBlocks) {
        return positions -> {
            if (positions.size() < minBlocks) {
                throw new ConstraintFailedException(
                    "The level does not have enough objects of the selected type"
                );
            }
        };
    }

}
