package arcaym.model.game.core.objects.api;

/**
 * Game objects specific types.
 */
public enum GameObjectType {

    /**
     * {@link GameObjectCategory#PLAYER} controlled by external input.
     */
    USER_PLAYER(GameObjectCategory.PLAYER),

    /**
     * {@link GameObjectCategory#GOAL} that marks the completition with success.
     */
    WIN_GOAL(GameObjectCategory.GOAL),

    /**
     * {@link GameObjectCategory#BLOCK} for defining level area.
     */
    FLOOR(GameObjectCategory.BLOCK),

    /**
     * {@link GameObjectCategory#BLOCK} for defining level borders.
     */
    WALL(GameObjectCategory.BLOCK),

    /**
     * {@link GameObjectCategory#COLLECTABLE} that increments score on contact.
     */
    COIN(GameObjectCategory.COLLECTABLE),

    /**
     * {@link GameObjectCategory#OBSTACLE} that triggers game over on contact.
     */
    SPIKE(GameObjectCategory.OBSTACLE),

    /**
     * {@link GameObjectCategory#OBSTACLE} that moves on the x axis and triggers
     * game over on contact.
     */
    MOVING_X_OBSTACLE(GameObjectCategory.OBSTACLE),

    /**
     * {@link GameObjectCategory#OBSTACLE} that moves on the y axis and triggers
     * game over on contact.
     */
    MOVING_Y_OBSTACLE(GameObjectCategory.OBSTACLE);

    private final GameObjectCategory category;

    GameObjectType(final GameObjectCategory category) {
        this.category = category;
    }

    /**
     * Get the object type major category.
     * 
     * @return type category
     */
    public GameObjectCategory category() {
        return this.category;
    }

}
