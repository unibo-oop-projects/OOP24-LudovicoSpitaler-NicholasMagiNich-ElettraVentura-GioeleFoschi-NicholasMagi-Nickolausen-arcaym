package arcaym.model.editor.impl;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import arcaym.model.editor.api.Cell;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * A cell implementation with three layers.
 * - One for floor type cells (Floor, blocks and goal)
 * - One for entities (enemies and player)
 * - One for collectables
 */
public class ThreeLayerCell implements Cell, Serializable {

    private static final long serialVersionUID = 1L; 

    private enum Layer { LOWERLAYER, ENTITYLAYER, COLLECTABLELAYER } 

    private final Map<Layer, GameObjectType> layers;

    /**
     * Initialize the cell with a default layer filled.
     * 
     * @param defaultLayer default layer fill
     */
    public ThreeLayerCell(final GameObjectType defaultLayer) {
        this.layers = new EnumMap<>(Layer.class);
        this.layers.put(Layer.LOWERLAYER, defaultLayer);
    }

    private ThreeLayerCell(final Map<Layer, GameObjectType> layers) {
        this.layers = layers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final GameObjectType type) {
        switch (type.category()) {
            case GameObjectCategory.BLOCK:
            case GameObjectCategory.GOAL:
                this.layers.put(Layer.LOWERLAYER, type);
                break;
            case GameObjectCategory.PLAYER:
            case GameObjectCategory.OBSTACLE:
                this.layers.put(Layer.ENTITYLAYER, type);
                break;
            case GameObjectCategory.COLLECTABLE:
                this.layers.put(Layer.COLLECTABLELAYER, type);
                break;
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObjectType> getValues() {
        return layers.entrySet().stream().sorted(Entry.comparingByKey()).map(Entry::getValue).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCopy() {
        return new ThreeLayerCell(Map.copyOf(layers));
    }
}
