package arcaym.model.game.core.objects.api;

/**
 * Game objects major categories.
 */
public enum GameObjectCategory {

    /**
     * Objects that define the level space and borders.
     */
    BLOCK,

    /**
     * Objects that mark the end of the level.
     */
    GOAL,

    /**
     * Objects that interfere with the completition of the level.
     */
    OBSTACLE,

    /**
     * Objects that try to complete the level.
     */
    PLAYER,

    /**
     * Objects that can be collected during the game.
     */
    COLLECTABLE

}
