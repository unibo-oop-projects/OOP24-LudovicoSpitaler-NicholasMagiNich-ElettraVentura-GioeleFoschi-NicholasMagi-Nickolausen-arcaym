package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import arcaym.common.point.api.Point;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.Memento;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An implementation fot the grid interface.
 */
public class GridImpl implements Grid {

    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR; // GameObjectType.WALL;
    private static final String PLAYER_CONSTRAINT_EXCEPTION_MESSAGE = "There can only be one player in the level";
    private static final String GOAL_CONSTRAINT_EXCEPTION_MESSAGE = "All Goal Cells must be adjacent";
    private static final String ILLEGAL_POSITION_EXCEPTION_MESSAGE = "The given position is out of the grid boundary";

    private Map<Point, GameObjectType> lowerLayer;
    private Map<Point, GameObjectType> enemyLayer;
    private Map<Point, GameObjectType> collectablesLayer;
    private final Point mapSize;

    /**
     * Creates a new Grid with the given dimentions.
     * @param x The width of the grid.
     * @param y The height of the grid.
     */
    public GridImpl(final int x, final int y) {
        this.lowerLayer = new HashMap<>();
        this.enemyLayer = new HashMap<>();
        this.collectablesLayer = new HashMap<>();
        this.mapSize = Point.of(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Memento takeSnapshot(final Collection<Point> positions, final GameObjectCategory changingTo) {
        return new ConcreteMemento(this, List.of(lowerLayer, enemyLayer, collectablesLayer));
    }

    /**
     * Restores the given state.
     * @param state the state to be restored
     */
    public void setState(final List<Map<Point, GameObjectType>> state) {
        this.lowerLayer = state.get(0);
        this.enemyLayer = state.get(1);
        this.collectablesLayer = state.get(2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObjects(final Collection<Point> positions, final GameObjectType type) throws EditorGridException {
        if (positions.stream().anyMatch(this::outsideBoundary)) {
            throw new EditorGridException(ILLEGAL_POSITION_EXCEPTION_MESSAGE);
        }
        switch (type.category()) {
            case GameObjectCategory.BLOCK :
            case GameObjectCategory.GOAL :
                if (!type.category().equals(GameObjectCategory.GOAL) || goalConstraint(positions)) {
                    setFromMap(positions, type, lowerLayer);
                } else {
                    throw new EditorGridException(GOAL_CONSTRAINT_EXCEPTION_MESSAGE);
                }
                break;
            case GameObjectCategory.OBSTACLE:
            case GameObjectCategory.PLAYER:
                if (!type.category().equals(GameObjectCategory.PLAYER) || playerConstraint(positions)) {
                    setFromMap(positions, type, enemyLayer);
                } else {
                    throw new EditorGridException(PLAYER_CONSTRAINT_EXCEPTION_MESSAGE);
                }
                break;
            case GameObjectCategory.COLLECTABLE:
                setFromMap(positions, type, collectablesLayer);
                break;
            default:
               break;
        }
    }

    private boolean outsideBoundary(final Point p) {
        return p.x() < 0 || p.x() > this.mapSize.x() || p.y() < 0 || p.y() > this.mapSize.y();
    }

    private boolean playerConstraint(final Collection<Point> positions) {
        return !enemyLayer.entrySet().stream().anyMatch(e -> e.getValue().equals(GameObjectType.USER_PLAYER))
            && positions.size() == 1;
    }

    private boolean goalConstraint(final Collection<Point> positions) {
        return isCluster(positions) && isNearbyPresentGoal(positions);
    }

    private boolean isCluster(final Collection<Point> positions) {
        return positions.stream()
            .allMatch(p -> positions.stream().anyMatch(pos -> adjacencyCondition(p, pos))) || positions.size() == 1;
    }

    private boolean adjacencyCondition(final Point p1, final Point p2) {
        return (p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y()) == 1;
    }

    private boolean isNearbyPresentGoal(final Collection<Point> positions) {
        final var currentGoal = lowerLayer
            .entrySet()
            .stream()
            .filter(e -> e.getValue().category().equals(GameObjectCategory.GOAL))
            .map(Entry::getKey).toList();
        return currentGoal.isEmpty()
            || positions.stream()
                .anyMatch(p -> currentGoal.stream().anyMatch(pos -> adjacencyCondition(p, pos)));
    }

    private void setFromMap(final Collection<Point> positions, final GameObjectType type, final Map<Point, GameObjectType> map) {
        positions.forEach(p -> map.put(p, type));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(final Collection<Point> positions) {
        removeFromMap(positions, lowerLayer);
        removeFromMap(positions, enemyLayer);
        removeFromMap(positions, collectablesLayer);
    }

    private void removeFromMap(final Collection<Point> positions, final Map<Point, GameObjectType> map) {
        positions.forEach(map::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObjectType> getObjects(final Point pos) {
        return Stream.concat(
            Stream.of(lowerLayer.containsKey(pos) ? lowerLayer.get(pos) : DEFAUL_TYPE), 
            Stream.concat(
                getObjectInPosition(enemyLayer, pos), 
                getObjectInPosition(collectablesLayer, pos)))
            .toList(); 
    }

    private Stream<GameObjectType> getObjectInPosition(final Map<Point, GameObjectType> map, final Point pos) {
        return map.entrySet()
            .stream()
            .filter(e -> e.getKey().equals(pos))
            .map(Entry::getValue);
    }
}
