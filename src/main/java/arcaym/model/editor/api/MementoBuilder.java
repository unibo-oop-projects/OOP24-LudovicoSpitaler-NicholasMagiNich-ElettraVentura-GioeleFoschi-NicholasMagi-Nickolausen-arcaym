package arcaym.model.editor.api;

import java.util.Map;

import arcaym.common.point.api.Point;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * A builder for the {@link Memento} class.
 */
public interface MementoBuilder {

    /**
     * The lower layer of the {@link Grid} changed, saving the old state for undo operation.
     * 
     * @param lowLayer All the position that changed.
     */
    void setWallsLayer(Map<Point, GameObjectType> lowLayer);

    /**
     * The enemy layer of the {@link Grid} changed, saving the old state for undo operation.
     * 
     * @param enemyLayer All the position that changed.
     */
    void setEnemyLayer(Map<Point, GameObjectType> enemyLayer);

    /**
     * The collectable layer of the {@link Grid} changed, saving the old state for undo operation.
     * 
     * @param collectableLayer All the position that changed.
     */
    void setCollectablesLayer(Map<Point, GameObjectType> collectableLayer);

    /**
     * Saves the entire current state of the {@link Grid}.
     * 
     * @param grid the grid to save
     */
    void saveAllState();

    /**
     * Builds the {@link Memento} object.
     * 
     * @return The memento object built
     */
    Memento buildMemento();
}
