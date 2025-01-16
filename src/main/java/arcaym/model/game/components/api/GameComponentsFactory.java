package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

/**
 * Interface for a Factory of Game Components.
 */
public interface GameComponentsFactory {

    /**
     * 
     * @return behaviour component of collision against an obstacle
     */
    public GameComponent obstacleCollision();

    /**
     * 
     * @return behaviour component of collision against a collectable
     */
    public GameComponent collectableCollision();

    /**
     * 
     * @return behaviour component of movement from input
     */
    public GameComponent fromInputMovement();

    /**
     * 
     * @return behaviour component of movement on X axis
     */
    public GameComponent automaticXMovement();

    /**
     * 
     * @return behaviour component of movement on Y axis
     */
    public GameComponent automaticYMovement();

    /**
     * 
     * @return behaviour component of collision with Goal
     */
    public GameComponent reachedGoal();

}
