package arcaym.model.editor.constraints;

import arcaym.model.editor.EditorType;

/**
 * An interface that adds constraints based on the {@link EditorType}.
 */
public interface GridConstraintProvider {

    /**
     * This methods adds little to no constraints.
     * 
     * @return The constraint container used by the grid to check the constraints.
     */
    GridConstraintsContainer sandbox();

    /**
     * This method adds several constraint for a normal grid.
     * 
     * @return The constraint container used by the grid to check the constraints.
     */
    GridConstraintsContainer normal();

    /**
     * Selects the right method to call based on the {@link EditorType} selected.
     * 
     * @param type The editor type selected
     * @return The constraint container used by the grid to check the constraints
     */
    default GridConstraintsContainer selectEditorType(
        final EditorType type
    ) {
        return switch (type) {
            case EditorType.SANDBOX -> sandbox();
            case EditorType.NORMAL -> normal();
        };
    }
}
