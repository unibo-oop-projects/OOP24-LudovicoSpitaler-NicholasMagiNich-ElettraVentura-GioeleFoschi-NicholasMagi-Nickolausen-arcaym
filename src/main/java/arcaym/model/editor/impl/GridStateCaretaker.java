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
    private int currentIndex;

    /**
     * Creates a caretaker object.
     */
    public GridStateCaretaker() {
        this.history = new ArrayList<>();
        this.currentIndex = -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSnapshot(final Memento state) {
        if (this.history.size() > currentIndex) {
            // if the current index is not the last, remove the "recovered states"
            this.history.subList(currentIndex + 1, this.history.size()).clear();
        }
        this.history.add(state);
        this.currentIndex++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Memento> recoverSnapshot() {
        if (this.currentIndex >= 0) {
            this.currentIndex--;
            return Optional.of(this.history.get(currentIndex + 1));
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return this.currentIndex >= 0;
    }
}
