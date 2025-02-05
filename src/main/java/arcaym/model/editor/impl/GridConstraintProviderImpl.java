package arcaym.model.editor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import arcaym.common.utils.Position;
import arcaym.model.editor.ConstraintFailedException;
import arcaym.model.editor.api.GridConstraintProvider;
import arcaym.model.editor.api.GridConstraintsContainer;
import arcaym.model.editor.api.MapConstraint;
import arcaym.model.editor.api.MapConstraintsFactory;
import arcaym.model.game.core.objects.GameObjectCategory;
import arcaym.model.game.objects.GameObjectType;

/**
 * An implementation of the {@link GridConstraintProvider} interface.
 */
public class GridConstraintProviderImpl implements GridConstraintProvider {

    private final MapConstraintsFactory constraintFactory = new MapConstraintFactoryImpl();

    private final class Container implements GridConstraintsContainer {

        /**
         * Position 0 is for normal constraints, position 1 is for the constraints to check before the play function.
         */
        private final List<Map<GameObjectType, MapConstraint>> objectConstraints = new ArrayList<>(2); 
        private final List<Map<GameObjectCategory, MapConstraint>> categoryConstraints = new ArrayList<>(2);

        private Container() {
            objectConstraints.add(new EnumMap<>(GameObjectType.class));
            objectConstraints.add(new EnumMap<>(GameObjectType.class));
            categoryConstraints.add(new EnumMap<>(GameObjectCategory.class));
            categoryConstraints.add(new EnumMap<>(GameObjectCategory.class));
        }

        private void addConstraint(final GameObjectType type, final MapConstraint con, final boolean isBeforePlayCheck) {
            if (isBeforePlayCheck) {
                objectConstraints.get(1).put(type, con);
            } else {
                objectConstraints.get(0).put(type, con);
            }
        }

        private void addConstraint(final GameObjectCategory category, final MapConstraint con, final boolean isBeforePlayCheck) {
            if (isBeforePlayCheck) {
                categoryConstraints.get(1).put(category, con);
            } else {
                categoryConstraints.get(0).put(category, con);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkConstraint(
            final Collection<Position> positions,
            final GameObjectType type) throws ConstraintFailedException {
            if (this.objectConstraints.get(0).containsKey(type)) {
                this.objectConstraints.get(0).get(type).checkConstraint(positions);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkConstraint(
            final Collection<Position> positions,
            final GameObjectCategory category) throws ConstraintFailedException {
            if (this.categoryConstraints.get(0).containsKey(category)) {
                this.categoryConstraints.get(0).get(category).checkConstraint(positions);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void checkBeforeStartConstraints(
            final Function<GameObjectType, Collection<Position>> typeMapProvider,
            final Function<GameObjectCategory, Collection<Position>> categoryMapProvider) throws ConstraintFailedException {
            for (final Entry<GameObjectType, MapConstraint> e : objectConstraints.get(1).entrySet()) {
                e.getValue().checkConstraint(typeMapProvider.apply(e.getKey()));
            }
            for (final Entry<GameObjectCategory, MapConstraint> e : categoryConstraints.get(1).entrySet()) {
                e.getValue().checkConstraint(categoryMapProvider.apply(e.getKey()));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GridConstraintsContainer sandbox() {
        return new Container();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GridConstraintsContainer normal() {
        final var constraints = new Container();
        final int maxCoin = 3;
        final int maxCollectable = 10;

        constraints.addConstraint(
            GameObjectType.COIN,
            this.constraintFactory.maxNumberOfBlocks(maxCoin, GameObjectType.COIN.toString()),
            false);
        constraints.addConstraint(
            GameObjectType.USER_PLAYER,
            this.constraintFactory.singleBlockConstraint(GameObjectType.USER_PLAYER.toString()),
            false);
        constraints.addConstraint(
            GameObjectCategory.GOAL,
            this.constraintFactory.adjacencyConstraint(),
            false);
        constraints.addConstraint(
            GameObjectCategory.COLLECTABLE,
            this.constraintFactory.maxNumberOfBlocks(maxCollectable, GameObjectCategory.COLLECTABLE.toString()),
            false);
        // set before play constraints
        constraints.addConstraint(
            GameObjectCategory.GOAL,
            this.constraintFactory.minNumberOfBlocks(1, GameObjectCategory.GOAL.toString()),
            true);
        constraints.addConstraint(
            GameObjectCategory.PLAYER,
            this.constraintFactory.minNumberOfBlocks(1, GameObjectCategory.PLAYER.toString()),
            true);

        return constraints;
    }
}
