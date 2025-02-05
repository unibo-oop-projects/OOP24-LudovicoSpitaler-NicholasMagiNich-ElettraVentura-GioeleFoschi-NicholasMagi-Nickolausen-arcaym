package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.api.GameComponent;

/**
 * Interface for a Factory of game components related to collisions.
 */
public interface CollisionComponentsFactory {
    /**
     * Handles a collision with an obstacle.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent obstacleCollision(ComponentsBasedGameObject gameObject);

    /**
     * Handles a collision with a collectable.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent collectableCollision(ComponentsBasedGameObject gameObject);

    /**
     * Handles a collision with the goal area.
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent reachedGoal(ComponentsBasedGameObject gameObject);
}
