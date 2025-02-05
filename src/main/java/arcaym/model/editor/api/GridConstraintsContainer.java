package arcaym.model.editor.api;

import java.util.Collection;
import java.util.function.Function;

import arcaym.common.utils.Position;
import arcaym.model.editor.ConstraintFailedException;
import arcaym.model.game.core.objects.GameObjectCategory;
import arcaym.model.game.objects.GameObjectType;

/**
 * A class containing all the constraints in a grid.
 */
public interface GridConstraintsContainer {
    /**
     * Checks the constraint of a specific {@link GameObjectType}.
     * @param positions The position on wich to apply the rule
     * @param type The type of the object.
     * @throws ConstraintFailedException If the constraint to check failed.
     */
    void checkConstraint(
        Collection<Position> positions,
        GameObjectType type) throws ConstraintFailedException;

    /**
     * Checks the constraint of a specific {@link GameObjectCategory}.
     * 
     * @param positions The position on wich to apply the rule
     * @param category  The category of the object.
     * @throws ConstraintFailedException If the constraint to check failed.
     */
    void checkConstraint(
        Collection<Position> positions,
        GameObjectCategory category) throws ConstraintFailedException;

    /**
     * Checks all the rules specific for the game start.
     * 
     * @param typeMapProvider A function that given a type returns a collection of position
     * @param categoryMapProvider A function that given a category returns a collection of position
     * @throws ConstraintFailedException If any of the constraints fail
     */
    void checkBeforeStartConstraints(
        Function<GameObjectType, Collection<Position>> typeMapProvider,
        Function<GameObjectCategory, Collection<Position>> categoryMapProvider) throws ConstraintFailedException;


        /**
     * Checks all the rules specific for the game start.
     * 
     * @param mapProvider A function that given
     * @throws ConstraintFailedException If the constraint to check failed.
     */
}
