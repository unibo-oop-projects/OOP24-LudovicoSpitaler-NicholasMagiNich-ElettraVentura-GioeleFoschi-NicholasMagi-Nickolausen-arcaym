package arcaym.model.game.core.objects.api;

/**
 * Game objects specific types.
 */
public enum GameObjectType {

    /**
     * 
     */
    PLAYER(GameObjectCategory.PLAYER),
    GOAL(GameObjectCategory.GOAL),
    FLOOR(GameObjectCategory.BLOCK),
    WALL(GameObjectCategory.BLOCK),
    COIN(GameObjectCategory.COLLECTABLE),
    SPIKE(GameObjectCategory.OBSTACLE);

    private final GameObjectCategory category;

    private GameObjectType(final GameObjectCategory category) {
        this.category = category;
    }

    public GameObjectCategory category() {
        return this.category;
    }

}