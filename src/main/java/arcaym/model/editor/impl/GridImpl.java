package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.EnumMap;
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
import arcaym.model.editor.api.MapConstraint;
import arcaym.model.editor.saves.MapSerializerImpl;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An implementation fot the grid interface.
 */
public class GridImpl implements Grid {

    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR; // GameObjectType.WALL;
    private static final String ILLEGAL_POSITION_EXCEPTION_MESSAGE = "Trying to place a block outside of the boundary";
    // private static final Logger LOGGER = LoggerFactory.getLogger(GridImpl.class);

    private final Map<Position, Cell> map;
    private final Map<GameObjectType, MapConstraint> objectConstraint = new EnumMap<>(GameObjectType.class);
    private final Map<GameObjectCategory, MapConstraint> categoryConstraint = new EnumMap<>(GameObjectCategory.class);
    private final Position mapSize;

    /**
     * Creates a new Grid with the given dimentions.
     * @param x The width of the grid.
     * @param y The height of the grid.
     * @param editorType The type of editor that needs to be created.
     */
    public GridImpl(final int x, final int y, final EditorType editorType) {
        this.map = new HashMap<>();
        this.mapSize = Position.of(x, y);
        addConstraints(editorType);
    }

    /**
     * Creates a new grid based on {@link LevelMetadata}.
     * @param metadata The data that is needed
     */
    public GridImpl(final LevelMetadata metadata) {
        this.mapSize = Position.of(metadata.size().x(), metadata.size().y());
        this.map = new MapSerializerImpl<Position, Cell>().loadMapFromBinaryFile(metadata.uuid());
        addConstraints(metadata.type());
    }

    private void addConstraints(final EditorType type) {
        new GridConstraintProviderImpl().selectEditorType(objectConstraint::put, categoryConstraint::put, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjects(final Collection<Position> positions, final GameObjectType type) throws EditorGridException {
        if (positions.stream().anyMatch(this::outsideBoundary)) {
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE, true);
        }
        try {
            if (objectConstraint.containsKey(type)) {
                final var mapOfType = getSetOfType(type);
                mapOfType.addAll(positions);
                objectConstraint.get(type).checkConstraint(mapOfType);
            }
            if (categoryConstraint.containsKey(type.category())) {
                final var mapOfCategory = getSetOfCategory(type.category());
                mapOfCategory.addAll(positions);
                categoryConstraint.get(type.category()).checkConstraint(mapOfCategory);
            }
        } catch (ConstraintFailedException e) {
            throw new EditorGridException(e.toString(), true, e);
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
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE, false);
        }
        for (final Entry<GameObjectType, MapConstraint> e : objectConstraint.entrySet()) {
            try {
                final var mapOfType = getSetOfType(e.getKey());
                mapOfType.removeAll(positions);
                e.getValue().checkConstraint(mapOfType);
            } catch (ConstraintFailedException ex) {
                throw new EditorGridException(ex.toString(), false, ex);
            }
        }
        for (final Entry<GameObjectCategory, MapConstraint> e : categoryConstraint.entrySet()) {
            try {
                final var mapOfCategory = getSetOfCategory(e.getKey());
                mapOfCategory.removeAll(positions);
                e.getValue().checkConstraint(mapOfCategory);
            } catch (ConstraintFailedException ex) {
                throw new EditorGridException(ex.toString(), false, ex);
            }
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
    public boolean saveState(final String uuid) {
        return new MapSerializerImpl<Position, Cell>().serializeMap(map, uuid);
    }
}
