package arcaym.model.editor.constraints;

import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.Traverser;

import arcaym.common.utils.Position;
import arcaym.model.editor.ConstraintFailedException;

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
            if (!positions.isEmpty()) {
                // create a map:
                // Cells are nodes, Edges are connection between neighbour cells 
                final MutableGraph<Position> checkGraph = GraphBuilder.directed().build();
                positions.forEach(checkGraph::addNode);
                positions.forEach(pos -> {
                    final var adjacent = positions
                        .stream()
                        .filter(p -> adjacencyCondition(p, pos))
                        .collect(Collectors.toSet());
                    adjacent.forEach(p -> checkGraph.putEdge(p, pos));
                });
                // If the intersection between the dfs exploration result and the original set
                // is not equal to the original set, it means some cells are not reachable from a random cell
                // Uses the Connected-component Labeling algorithm
                if (!Sets.intersection(
                        Sets.newHashSet(
                            Traverser.forGraph(checkGraph).depthFirstPostOrder(positions.iterator().next())),
                            positions.stream().collect(Collectors.toSet())).equals(positions)
                    ) {
                        throw new ConstraintFailedException("All the cells placed must be nearby");
                } 
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
    public MapConstraint singleBlockConstraint(final String objectError) {
        return maxNumberOfBlocks(1, objectError);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MapConstraint maxNumberOfBlocks(final int maxBlocks, final String objectError) {
        return positions -> {
            if (positions.size() > maxBlocks) {
                throw new ConstraintFailedException("Maximum amount of " + objectError + " placed exceeded: Max = "
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
    public MapConstraint minNumberOfBlocks(final int minBlocks, final String objectError) {
        return positions -> {
            if (positions.size() < minBlocks) {
                throw new ConstraintFailedException(
                    "The level does not have enough objects of type: " + objectError
                );
            }
        };
    }

}
