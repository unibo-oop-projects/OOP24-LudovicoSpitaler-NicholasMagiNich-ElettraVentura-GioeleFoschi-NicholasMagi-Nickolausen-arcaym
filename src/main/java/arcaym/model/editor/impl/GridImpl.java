package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import arcaym.common.utils.Position;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.model.editor.ConstraintFailedException;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.api.Cell;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.GridConstraintsContainer;
import arcaym.model.editor.api.Memento;
import arcaym.model.editor.saves.MapSerializerImpl;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An implementation fot the grid interface.
 */
public class GridImpl implements Grid {

    private static final String ILLEGAL_POSITION_EXCEPTION_MESSAGE = "Trying to place a block outside of the boundary";
    private static final String PLACED_MESSAGE = "place objects";
    private static final String REMOVED_MESSAGE = "removed objects";
    private static final String CANNOT_PLAY_MESSAGE = "play";

    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR; // GameObjectType.WALL;

    private final Map<Position, Cell> map;
    private final Position mapSize;
    private final GridConstraintsContainer constraints;

    /**
     * Creates a new Grid with the given dimentions.
     * @param x The width of the grid.
     * @param y The height of the grid.
     * @param editorType The type of editor that needs to be created.
     */
    public GridImpl(final int x, final int y, final EditorType editorType) {
        this.map = new HashMap<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                map.put(Position.of(i, j), new ThreeLayerCell(DEFAUL_TYPE));
            }
        }
        this.mapSize = Position.of(x, y);
        this.constraints = new GridConstraintProviderImpl().selectEditorType(editorType);
    }

    /**
     * Creates a new grid based on {@link LevelMetadata}.
     * @param metadata The data that is needed
     */
    public GridImpl(final LevelMetadata metadata) {
        this.mapSize = Position.of(metadata.size().x(), metadata.size().y());
        this.map = new MapSerializerImpl<Position, Cell>().loadMapFromBinaryFile(metadata.uuid());
        this.constraints = new GridConstraintProviderImpl().selectEditorType(metadata.type());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getSize() {
        return Position.of(this.mapSize.x(), this.mapSize.y());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjects(final Collection<Position> positions, final GameObjectType type) throws EditorGridException {
        if (positions.stream().anyMatch(this::outsideBoundary)) {
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE, PLACED_MESSAGE);
        }
        try {
            final var mapOfType = getSetOfType(type);
            mapOfType.addAll(positions);
            this.constraints.checkConstraint(mapOfType, type);
            final var mapOfCategory = getSetOfCategory(type.category());
            mapOfCategory.addAll(positions);
            this.constraints.checkConstraint(mapOfCategory, type.category());
        } catch (ConstraintFailedException e) {
            throw new EditorGridException(e.toString(), PLACED_MESSAGE, e);
        }

        positions.forEach(pos -> {
            if (!map.containsKey(pos)) {
                map.put(pos, new ThreeLayerCell(DEFAUL_TYPE));
            }
            map.get(pos).setValue(type);
        });
    }

    private Set<Position> getSetOfCategory(final GameObjectCategory category) {
        return map
            .entrySet()
            .stream()
            .filter(e -> e.getValue().getValues().stream().map(GameObjectType::category).toList().contains(category))
            .map(Entry::getKey)
            .collect(Collectors.toSet());
    }

    private Set<Position> getSetOfType(final GameObjectType type) {
        return map.entrySet()
            .stream()
            .filter(e -> e.getValue().getValues().contains(type))
            .map(Entry::getKey)
            .collect(Collectors.toSet());
    }

    private boolean outsideBoundary(final Position p) {
        return p.x() < 0 || p.x() > this.mapSize.x() || p.y() < 0 || p.y() > this.mapSize.y();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(final Collection<Position> positions) throws EditorGridException {
        if (positions.stream().anyMatch(this::outsideBoundary)) {
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE, REMOVED_MESSAGE);
        }

        try {
            for (final GameObjectType type : EnumSet.allOf(GameObjectType.class)) {
                final var mapOfType = getSetOfType(type);
                mapOfType.removeAll(positions);
                this.constraints.checkConstraint(mapOfType, type);
            }
            for (final GameObjectCategory cat : EnumSet.allOf(GameObjectCategory.class)) {
                final var mapOfType = getSetOfCategory(cat);
                mapOfType.removeAll(positions);
                this.constraints.checkConstraint(mapOfType, cat);
            }
        } catch (ConstraintFailedException e) {
            throw new EditorGridException(e.toString(), REMOVED_MESSAGE, e);
        }
        positions.forEach(map::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObjectType> getObjects(final Position pos) {
        return map.containsKey(pos) ? map.get(pos).getValues() : List.of(DEFAUL_TYPE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void canPlay() throws EditorGridException {
        try {
            this.constraints.checkBeforeStartConstraints(this::getSetOfType, this::getSetOfCategory);
        } catch (ConstraintFailedException e) {
            throw new EditorGridException(e.toString(), CANNOT_PLAY_MESSAGE, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveState(final String uuid) {
        return new MapSerializerImpl<Position, Cell>().serializeMap(map, uuid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Memento takeSnapshot(final Collection<Position> positions) {
        return new GridMemento(positions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Position> recoverSavedState(final Memento state) {
        if (state instanceof GridMemento) {
            // not checking for constraints as recovering the previous state should never fail constraints.
            final var recoveredState = ((GridMemento) state).getState();
            this.map.putAll(recoveredState);
            return recoveredState.keySet();
        }
        return Collections.emptySet();
    }

    private final class GridMemento implements Memento {

        private final Map<Position, Cell> changedCells;

        private GridMemento(final Collection<Position> pos) {
            this.changedCells = map.entrySet()
                .stream()
                .filter(e -> pos.contains(e.getKey()))
                .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().getCopy()));
        }

        private Map<Position, Cell> getState() {
            return Map.copyOf(changedCells);
            // Older implementation of the memento, that also checked for constraints.
            // Changed for performance issues
            // Saving it, because it was beautiful
            // from the map of Position, List<GameObjectType> creates a Map<GameObjectType, List<Position>>
            // for easier recovery of the old state;
            // return changedCells.entrySet()
            //     .stream()
            //     .flatMap(e -> 
            //         e.getValue()
            //             .stream()
            //             .map(gameObject -> new AbstractMap.SimpleEntry<GameObjectType, Position>(gameObject, e.getKey())))
            //     .collect(Collectors.groupingBy(
            //         Entry::getKey, 
            //         Collector.of(
            //             HashSet::new,
            //             (set, entry) -> set.add(entry.getValue()),
            //             (set1, set2) -> {
            //                 set1.addAll(set2); return set1;
            //             }
            //     )));
        }
    }
}
