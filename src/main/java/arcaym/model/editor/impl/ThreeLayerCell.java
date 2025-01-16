package arcaym.model.editor.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import arcaym.model.editor.api.Cell;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * A cell implementation with three layers.
 * - One for floor type cells (Floor, blocks and goal)
 * - One for entities (enemies and player)
 * - One for collectables
 */
public class ThreeLayerCell implements Cell {

    private Optional<GameObjectType> lowerLayer;
    private Optional<GameObjectType> entityLayer;
    private Optional<GameObjectType> collectableLayer;
    private final GameObjectType defaultLayer;

    /**
     * 
     * @param defaultLayer
     */
    public ThreeLayerCell(final GameObjectType defaultLayer) {
        this.defaultLayer = defaultLayer;
        this.lowerLayer = Optional.empty();
        this.entityLayer = Optional.empty();
        this.collectableLayer = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final GameObjectType type) {
        switch (type.category()) {
            case GameObjectCategory.BLOCK:
            case GameObjectCategory.GOAL:
                this.lowerLayer = Optional.of(type);
                break;
            case GameObjectCategory.PLAYER:
            case GameObjectCategory.OBSTACLE:
                this.entityLayer = Optional.of(type);
                break;
            case GameObjectCategory.COLLECTABLE:
                this.collectableLayer = Optional.of(type);
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObjectType> getValues() {
        final var outList = new ArrayList<GameObjectType>();
        outList.add(lowerLayer.isPresent() ? lowerLayer.get() : defaultLayer);
        if (entityLayer.isPresent()) {
            outList.add(entityLayer.get());
        }
        if (collectableLayer.isPresent()) {
            outList.add(collectableLayer.get());
        }
        return outList;
    }
}
