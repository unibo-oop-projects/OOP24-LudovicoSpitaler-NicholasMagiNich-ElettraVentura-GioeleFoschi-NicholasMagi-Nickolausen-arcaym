package arcaym.model.game.core.objects.api;

/**
 * Game objects major categories.
 */
public enum GameObjectCategory {

    /**
     * Contruction object for the definition of level space and borders.
     */
    BLOCK,

    /**
     * Special block that marks the end of the level.
     */
    GOAL,

    /**
     * Object that interferse with the completition of the level.
     */
    OBSTACLE,

    /**
     * Object that needs to reach a {@link GameObjectType#GOAL} to win.
     */
    PLAYER,

    /**
     * Object that manipulates score on contact.
     */
    COLLECTABLE

}
