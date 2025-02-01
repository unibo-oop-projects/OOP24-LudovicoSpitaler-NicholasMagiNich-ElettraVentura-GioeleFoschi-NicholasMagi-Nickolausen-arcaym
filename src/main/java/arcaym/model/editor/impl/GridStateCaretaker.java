package arcaym.model.editor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import arcaym.model.editor.api.Grid;
import arcaym.model.editor.api.Memento;
import arcaym.model.editor.api.MementoCaretaker;

/**
 * A class that saves all the previous states of a {@link Grid}.
 */
public class GridStateCaretaker implements MementoCaretaker {

    private final List<Memento> history;

    /**
     * Creates a caretaker object.
     */
    public GridStateCaretaker() {
        this.history = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSnapshot(final Memento state) {
        this.history.addLast(state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Memento> recoverSnapshot() {
        if (!this.history.isEmpty()) {
            return Optional.of(this.history.removeLast());
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return !this.history.isEmpty();
    }
}
