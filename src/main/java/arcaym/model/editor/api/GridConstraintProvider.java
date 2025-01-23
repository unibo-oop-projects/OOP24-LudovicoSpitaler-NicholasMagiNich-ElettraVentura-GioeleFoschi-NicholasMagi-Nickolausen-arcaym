package arcaym.model.editor.api;

import java.util.function.BiConsumer;

import arcaym.model.editor.EditorType;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An interface that adds constraints based on the {@link EditorType}.
 */
public interface GridConstraintProvider {

    /**
     * This methods adds little to no constraints.
     * 
     * @param object The target for object constraint
     * @param category The target for category constraint
     */
    void sandbox(
        BiConsumer<GameObjectType, MapConstraint> object, 
        BiConsumer<GameObjectCategory, MapConstraint> category);

    /**
     * This method adds several constraint for a normal grid.
     * 
     * @param object The target for object constraint
     * @param category The target for category constraint
     */
    void normal(
        BiConsumer<GameObjectType, MapConstraint> object,
        BiConsumer<GameObjectCategory, MapConstraint> category);

    /**
     * Selects the right method to call based on the {@link EditorType} selected.
     * 
     * @param object   The target for object constraint
     * @param category The target for category constraint
     * @param type The type selected
     */
    default void selectEditorType(
        final BiConsumer<GameObjectType, MapConstraint> object,
        final BiConsumer<GameObjectCategory, MapConstraint> category,
        final EditorType type
    ) {
        switch (type) {
            case EditorType.SANDBOX:
                sandbox(object, category);
                break;
            case EditorType.NORMAL:
                normal(object, category);
                break;
            default:
                break;
        }
    }
}
