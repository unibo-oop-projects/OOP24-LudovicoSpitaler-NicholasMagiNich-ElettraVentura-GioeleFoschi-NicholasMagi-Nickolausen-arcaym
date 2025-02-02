package arcaym.controller.editor.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import arcaym.common.geometry.impl.Point;
import arcaym.common.utils.Optionals;
import arcaym.common.utils.Position;
import arcaym.controller.editor.api.GridController;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.editor.saves.MetadataManagerImpl;
import arcaym.model.editor.EditorGridException;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.api.GridModel;
import arcaym.model.editor.impl.GridModelImpl;
import arcaym.model.game.components.impl.ComponentsBasedObjectsFactory;
import arcaym.model.game.core.engine.impl.FactoryBasedGameBuilder;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * An editor implementation with no undo feature.
 */
public class GridWithoudUndo implements GridController {

    private GameObjectType selectedObject = GameObjectType.FLOOR;
    private final GridModel grid;
    private final LevelMetadata metadata;
    private Optional<Consumer<Map<Position, List<GameObjectType>>>> view;

    /**
     * Creates an editor controller without the ability do undo / redo.
     * @param x Width of the grid
     * @param y Height of the grid
     * @param type The type of grid that needs to be created
     * @param name The name to give to the level
     */
    public GridWithoudUndo(
        final int x, 
        final int y, 
        final EditorType type,
        final String name) {
        this.grid = new GridModelImpl(type, x, y);
        this.metadata = new LevelMetadata(
            name,
            UUID.randomUUID().toString(),
            type,
            Position.of(x, y));
        this.view = Optional.empty();
    }

    /**
     * Creates an editor controller starting from a metadata object.
     * @param metadata The object with internal data.
     */
    public GridWithoudUndo(
        final LevelMetadata metadata) {
        this.grid = new GridModelImpl(metadata);
        this.metadata = new LevelMetadata(
            metadata.levelName(),
            metadata.uuid(),
            metadata.type(),
            metadata.size());
        this.view = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void play() {
        final var gameFactory = new FactoryBasedGameBuilder(new ComponentsBasedObjectsFactory(10)); // 10 is temporary
        final var objectsInPosition = this.grid.getFullMap();
        objectsInPosition.entrySet().forEach(e -> {
            e.getValue()
                .forEach(type -> gameFactory.addObject(type, Point.of(e.getKey().x(), e.getKey().y())));
        });
        // return gameFactory.build();
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
    public boolean canUndo() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void eraseArea(final Collection<Position> positions) throws EditorGridException {
        this.grid.removeObjects(positions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyChange(final Collection<Position> positions) throws EditorGridException {
        this.grid.placeObjects(positions, selectedObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveLevel() {
        return this.grid.saveState(metadata.uuid())
            && new MetadataManagerImpl().saveMetadata(metadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final Consumer<Map<Position, List<GameObjectType>>> listener) {
        this.view = Optional.of(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateView(final Map<Position, List<GameObjectType>> map) {
        final var update = Optionals.orIllegalState(this.view, "View listener not initialized");
        update.accept(map);
    }
}
