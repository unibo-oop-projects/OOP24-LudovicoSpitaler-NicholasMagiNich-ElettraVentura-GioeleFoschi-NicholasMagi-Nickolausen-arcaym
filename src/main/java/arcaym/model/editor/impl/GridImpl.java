package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import arcaym.common.point.api.Point;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.api.Cell;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.MapContsraint;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An implementation fot the grid interface.
 */
public class GridImpl implements Grid {

    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR; // GameObjectType.WALL;
    // private static final String PLAYER_CONSTRAINT_EXCEPTION_MESSAGE = "There can only be one player in the level";
    // private static final String GOAL_CONSTRAINT_EXCEPTION_MESSAGE = "All Goal Cells must be adjacent";
    private static final String ILLEGAL_POSITION_EXCEPTION_MESSAGE = "The given position is out of the grid boundary";

    private final Map<Point, Cell> map;
    private final Map<GameObjectType, MapContsraint> objectConstraint;
    private final Map<GameObjectCategory, MapContsraint> categoryConstraint;
    private final Point mapSize;

    /**
     * Creates a new Grid with the given dimentions.
     * @param x The width of the grid.
     * @param y The height of the grid.
     */
    public GridImpl(final int x, final int y) {
        this.map = new HashMap<>();
        this.objectConstraint = new EnumMap<>(GameObjectType.class);
        this.categoryConstraint = new EnumMap<>(GameObjectCategory.class);
        this.mapSize = Point.of(x, y);
        for (int i = 0; i < mapSize.x(); i++) {
            for (int j = 0; j < mapSize.y(); j++) {
                map.put(Point.of(x, y), new ThreeLayerCell(DEFAUL_TYPE));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjectConstraint(final MapContsraint contsraint, final GameObjectType target) {
        objectConstraint.put(target, contsraint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCategoryConstraint(final MapContsraint contsraint, final GameObjectCategory target) {
        categoryConstraint.put(target, contsraint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjects(final Collection<Point> positions, final GameObjectType type) throws EditorGridException {
        if (positions.stream().anyMatch(this::outsideBoundary)) {
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE);
        }
        if (objectConstraint.containsKey(type)) {
            final var mapOfType = getMapOfType(type);
            mapOfType.addAll(positions);
            objectConstraint.get(type).checkConstraint(mapOfType);
        }
        if (categoryConstraint.containsKey(type.category())) {
            final var mapOfCategory = getMapOfCategory(type.category());
            mapOfCategory.addAll(positions);
            categoryConstraint.get(type.category()).checkConstraint(mapOfCategory);
        }
        positions.forEach(pos -> map.get(pos).setValue(type));
    }

    private Set<Point> getMapOfCategory(final GameObjectCategory category) {
        return map
            .entrySet()
            .stream()
            .filter(e -> e.getValue().getValues().stream().map(GameObjectType::category).toList().contains(category))
            .map(Entry::getKey)
            .collect(Collectors.toSet());
    }

    private Set<Point> getMapOfType(final GameObjectType type) {
        return map.entrySet()
            .stream()
            .filter(e -> e.getValue().getValues().contains(type))
            .map(Entry::getKey)
            .collect(Collectors.toSet());
    }

    private boolean outsideBoundary(final Point p) {
        return p.x() < 0 || p.x() > this.mapSize.x() || p.y() < 0 || p.y() > this.mapSize.y();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(final Collection<Point> positions) {
        // check constraints;
        positions.forEach(pos -> map.put(pos, new ThreeLayerCell(DEFAUL_TYPE)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObjectType> getObjects(final Point pos) {
        return map.get(pos).getValues();
    }

}
