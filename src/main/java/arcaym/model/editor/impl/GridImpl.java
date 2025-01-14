package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import arcaym.common.point.api.Point;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.Memento;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

public class GridImpl implements Grid{

    private static final GameObjectType DEFAUL_TYPE = GameObjectType.FLOOR; // GameObjectType.WALL;

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

    @Override
    public Memento takeSnapshot(Collection<Point> positions, GameObjectCategory changingTo) {
        return null;//new MementoImpl(lowerLayer, enemyLayer, collectablesLayer);
    }

    /**
     * Restores the given state.
     * @param state the state to be restored
     */
    public void setState(List<Map<Point, GameObjectType>> state){
        this.lowerLayer = state.get(0);
        this.enemyLayer = state.get(1);
        this.collectablesLayer = state.get(2);
    }

    @Override
    public void setObjects(Collection<Point> positions, GameObjectType type) {
        switch (type.category()) {
            case GameObjectCategory.BLOCK:
            case GameObjectCategory.GOAL:
                if(!type.category().equals(GameObjectCategory.GOAL) || goalConstraint(positions, mapSize)){
                    setFromMap(positions, type, lowerLayer);
                }
                break;
            case GameObjectCategory.OBSTACLE:
            case GameObjectCategory.PLAYER:
                if(!type.category().equals(GameObjectCategory.PLAYER) || playerConstraint(positions, mapSize)){
                    setFromMap(positions, type, enemyLayer);
                }
                break;
            case GameObjectCategory.COLLECTABLE:
                setFromMap(positions, type, collectablesLayer);
                break;
            default:
                break;
        }
    }

    private void setFromMap(Collection<Point> positions, GameObjectType type, Map<Point,GameObjectType> map) {
        for (Point point : positions) {
            map.put(point, type);
        }
    }

    @Override
    public void removeObjects(Collection<Point> positions) {
        removeFromMap(positions, lowerLayer);
        removeFromMap(positions, enemyLayer);
        removeFromMap(positions, collectablesLayer);
    }

    private void removeFromMap(Collection<Point> positions, Map<Point, GameObjectType> map){
        for (Point point : positions) {
            map.remove(point);
        }
    }

    @Override
    public Collection<GameObjectType> getObjects(Point pos) {
        // TODO develop a better solution
        var objectSet = new HashSet<GameObjectType>();
        objectSet.add(lowerLayer.containsKey(pos) ? lowerLayer.get(pos) : DEFAUL_TYPE);
        objectSet.addAll(Stream.concat(
            enemyLayer.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(pos))
                .map(e -> e.getValue()),
            enemyLayer.entrySet()
                .stream()
                .filter(e -> e.getKey().equals(pos))
                .map(e -> e.getValue())
            ).toList());
        return objectSet;
    }

    // public static class Memento {
    //     private Map<Point, GameObjectType> ll;
    //     private Map<Point, GameObjectType> el;
    //     private Map<Point, GameObjectType> cl;

    //     private Memento(
    //         Map<Point, GameObjectType> ll, 
    //         Map<Point, GameObjectType> el, 
    //         Map<Point, GameObjectType> cl
    //         ){
    //         this.ll = ll;
    //         this.el = el;
    //         this.cl = cl;
    //     }
    // }
}
