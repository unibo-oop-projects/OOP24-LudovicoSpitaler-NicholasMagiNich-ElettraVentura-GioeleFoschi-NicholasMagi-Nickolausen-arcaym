package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

/**
 * Interface for a Factory of game components related to collisions.
 */
public interface CollisionComponentsFactory {
    /**
     * Handles a collision with an obstacle.
     * @return said behaviour as a game component
     */
    GameComponent obstacleCollision();

    /**
     * Handles a collision with a collectable.
     * @return said behaviour as a game component
     */
    GameComponent collectableCollision();

    /**
     * Handles a collision with the goal area.
     * @return said behaviour as a game component
     */
    GameComponent reachedGoal();
}
