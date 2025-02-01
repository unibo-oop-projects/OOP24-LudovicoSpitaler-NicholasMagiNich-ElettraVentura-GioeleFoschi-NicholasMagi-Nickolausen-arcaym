package arcaym.model.editor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import arcaym.common.utils.Position;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.GridModel;
import arcaym.model.game.objects.api.GameObjectType;
/**
 * An implementation of the grid model.
 */
public class GridModelImpl implements GridModel {

    private final Grid grid;
    private Set<Position> changedState = Collections.emptySet();
    private final GridStateCaretaker history;

    /**
     * Creates a new grid model starting from an empty map.
     * @param type The type of the editor: sandbox or normal
     * @param x the width of the new editor
     * @param y the height of the new editor
     */
    public GridModelImpl(
        final EditorType type,
        final int x,
        final int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Grid diemnsion cannot be less than zero");
        }
        this.grid = new GridImpl(x, y, type);
        this.history = new GridStateCaretaker();
    }

    /**
     * Builds a grid model starting from a metadata file.
     * @param metadata The file containing the informations
     */
    public GridModelImpl(final LevelMetadata metadata) {
        this.grid = new GridImpl(metadata);
        this.history = new GridStateCaretaker();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        this.changedState = this.grid.recoverSavedState(this.history.recoverSnapshot().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return this.history.canUndo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeObjects(final Collection<Position> positions, final GameObjectType type) throws EditorGridException {
        this.history.saveSnapshot(this.grid.takeSnapshot(positions));
        this.changedState = positions.stream().collect(Collectors.toSet());
        this.grid.setObjects(positions, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(final Collection<Position> positions) throws EditorGridException {
        this.history.saveSnapshot(this.grid.takeSnapshot(positions));
        this.changedState = positions.stream().collect(Collectors.toSet());
        this.grid.removeObjects(positions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, List<GameObjectType>> getUpdatedGrid() {
        return changedState
            .stream()
            .collect(Collectors.toMap(p -> p, grid::getObjects));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveState(final String uuid) {
        return this.grid.saveState(uuid);
    }
}
