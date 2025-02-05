package arcaym.model.game.core.objects;

import arcaym.model.game.objects.GameObjectType;

/**
 * {@link GameObjectType} major categories.
 */
public enum GameObjectCategory {

    /**
     * Objects that defines the level space and borders.
     */
    BLOCK,

    /**
     * Objects that marks the end of the level.
     */
    GOAL,

    /**
     * Objects that interferes with the completition of the level.
     */
    OBSTACLE,

    /**
     * Objects that tries to complete the level.
     */
    PLAYER,

    /**
     * Objects that can be collected during the game.
     */
    COLLECTABLE

}
