package arcaym.model.editor.api;

/**
 * A factory for the possible constraints of the map.
 */
public interface MapConstraintsFactory {

    /**
     * Creates a constraint with the following rule.
     * - All the given cells must be adjacent 
     * @return A constraint where all the cell must be adjacent;
     */
    MapContsraint adjacencyConstraint();
}
