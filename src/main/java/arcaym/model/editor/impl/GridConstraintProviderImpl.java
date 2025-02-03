package arcaym.model.editor.impl;

import java.util.function.BiConsumer;

import arcaym.model.editor.api.GridConstraintProvider;
import arcaym.model.editor.api.MapConstraint;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An implementation of the {@link GridConstraintProvider} interface.
 */
public class GridConstraintProviderImpl implements GridConstraintProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public void sandbox(
        final BiConsumer<GameObjectType, MapConstraint> object, 
        final BiConsumer<GameObjectCategory, MapConstraint> category) {
        // free to place objects without rules
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void normal(
            final BiConsumer<GameObjectType, MapConstraint> object,
            final BiConsumer<GameObjectCategory, MapConstraint> category) {
        final var constraintFactory = new MapConstraintFactoryImpl();
        object.accept(GameObjectType.COIN, constraintFactory.maxNumberOfBlocks(3));
        object.accept(GameObjectType.USER_PLAYER, constraintFactory.singleBlockConstraint());
        category.accept(GameObjectCategory.COLLECTABLE, constraintFactory.maxNumberOfBlocks(10));
        category.accept(GameObjectCategory.GOAL, constraintFactory.adjacencyConstraint());
    }
}
