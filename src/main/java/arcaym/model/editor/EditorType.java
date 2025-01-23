package arcaym.model.editor;

import arcaym.model.editor.api.Grid;

/**
 * An enum class describing every type of grid that you can have.
 */
public enum EditorType {

    /**
     * This type of {@link Grid} has little to no constraints.
     */
    SANDBOX(),

    /**
     * This type of {@link Grid} has several constraint on placement of objects.
     */
    NORMAL();

}
