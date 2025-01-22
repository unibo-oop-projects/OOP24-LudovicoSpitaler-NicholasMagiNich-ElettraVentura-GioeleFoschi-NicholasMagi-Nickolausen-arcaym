package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

public interface CollisionComponentsFactory {
    public GameComponent obstacleCollision();

    public GameComponent collectableCollision();

    public GameComponent reachedGoal();
}
