package arcaym.controller.editor.impl;

import java.util.Collection;
import java.util.List;

import arcaym.common.utils.Position;
import arcaym.controller.editor.api.Editor;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.impl.GridImpl;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An editor implementation with no undo feature.
 */
public class EditorWithoutUndo implements Editor {

    private GameObjectType selectedObject;
    private List<GameObjectType> availableObjectList;
    private final Grid editorGrid;

    /**
     * Creates an editor controller without the ability do undo / redo.
     * @param startingObject the object automatically selected at the start
     * @param x Width of the grid
     * @param y Height of the grid
     * @param type The type of grid that needs to be created
     */
    public EditorWithoutUndo(
        final GameObjectType startingObject, 
        final int x, 
        final int y, 
        final EditorType type) {
        this.selectedObject = startingObject;
        this.editorGrid = new GridImpl(x, y, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateListOfAvailableObjects(final List<GameObjectType> availableList) {
        this.availableObjectList = List.copyOf(availableList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObjectType> getListOfAvailableObjects() {
        return this.availableObjectList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedObject(final GameObjectType object) {
        this.selectedObject = object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redo() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRedo() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eraseArea(final Collection<Position> positions) throws EditorGridException {
        this.editorGrid.removeObjects(positions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyChange(final Collection<Position> positions) throws EditorGridException {
        this.editorGrid.setObjects(positions, selectedObject);
    }
}
