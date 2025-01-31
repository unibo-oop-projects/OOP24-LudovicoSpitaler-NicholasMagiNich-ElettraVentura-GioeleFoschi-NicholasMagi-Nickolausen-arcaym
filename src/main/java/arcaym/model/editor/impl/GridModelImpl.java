package arcaym.model.editor.impl;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import arcaym.common.utils.Position;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.GridModel;
import arcaym.model.editor.api.Memento;
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
        final var memento = (GridMemento) this.history.recoverSnapshot().get();
        memento.getState().entrySet()
            .forEach(e -> {
                // e.getValue()
                //     .forEach(object -> placeObjects(changedState, object));
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return false; // tmp
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redo() {
        // memento recover
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canRedo() {
        return false; // tmp
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeObjects(final Collection<Position> positions, final GameObjectType type) throws EditorGridException {
        this.changedState = positions.stream().collect(Collectors.toSet());
        this.grid.setObjects(positions, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(final Collection<Position> positions) throws EditorGridException {
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

    private final class GridMemento implements Memento {

        private final Map<Position, List<GameObjectType>> changedCells;

        private GridMemento(final Collection<Position> pos) {
            this.changedCells = pos
                .stream()
                .collect(Collectors.toMap(p -> p, grid::getObjects));
        }

        private Map<GameObjectType, Collection<Position>> getState() {
            // from the map of Position, List<GameObjectType> creates a Map<GameObjectType, List<Position>>
            // for easier recovery of the old state;
            return changedCells.entrySet()
                .stream()
                .flatMap(e -> 
                    e.getValue()
                        .stream()
                        .map(gameObject -> new AbstractMap.SimpleEntry<GameObjectType, Position>(gameObject, e.getKey())))
                .collect(Collectors.groupingBy(
                    Entry::getKey, 
                    Collector.of(
                        HashSet::new,
                        (set, entry) -> set.add(entry.getValue()),
                        (set1, set2) -> {
                            set1.addAll(set2); return set1;
                        }
                )));
        }
    }
}
