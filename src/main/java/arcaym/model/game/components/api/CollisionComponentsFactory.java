package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;

/**
 * Interface for a Factory of game components related to collisions.
 */
public interface CollisionComponentsFactory {
    /**
     * Handles a collision with an obstacle.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent obstacleCollision(UniqueComponentsGameObject gameObject);

    /**
     * Handles a collision with a collectable.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent collectableCollision(UniqueComponentsGameObject gameObject);

    /**
     * Handles a collision with the goal area.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent reachedGoal(UniqueComponentsGameObject gameObject);
}
