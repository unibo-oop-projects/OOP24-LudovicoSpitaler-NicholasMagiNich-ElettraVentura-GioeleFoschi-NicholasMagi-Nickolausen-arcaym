package arcaym.model.editor.api;

/**
 * A factory for the possible constraints of the map.
 */
public interface MapConstraintsFactory {

    /**
     * Creates a {@link MapConstraint} with the following rule.
     * - All the given cells must be adjacent
     * 
     * @return A {@link MapConstraint} with the above rule
     */
    MapConstraint adjacencyConstraint();

    /**
     * Creates a {@link MapConstraint} with the following rule.
     * - There can only be one block of the type
     * 
     * @return A {@link MapConstraint} with the above rule
     */
    MapConstraint singleBlockConstraint();

    /**
     * Creates a {@link MapConstraint} with the following rule.
     * - There can only be a maximum amount of blocks of a type.
     * 
     * @param maxBlocks The maximum amount of blocks that can be placed 
     * @return A {@link MapConstraint} with the above rule
     */
    MapConstraint maxNumberOfBlocks(int maxBlocks);

    /**
     * Creates a {@link MapConstraint} with the following rule.
     * - There must be a minimum amount of blocks of a type.
     * 
     * This rule's effectiveness is at its peack when used to check, before 
     * starting the level, if every must-have block is present
     * 
     * @param minBlocks The minimum amount of blocks that can be placed
     * @return A {@link MapConstraint} with the above rule
     */
    MapConstraint minNumberOfBlocks(int minBlocks);
}
