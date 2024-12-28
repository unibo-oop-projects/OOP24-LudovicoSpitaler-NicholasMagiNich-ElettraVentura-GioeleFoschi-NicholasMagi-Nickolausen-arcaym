package arcaym.model.game.objects;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Game objects specific types.
 */
public enum GameObjectType {

    /**
     * 
     */
    USER_PLAYER(GameObjectCategory.PLAYER),
    GOAL_TILE(GameObjectCategory.GOAL),
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