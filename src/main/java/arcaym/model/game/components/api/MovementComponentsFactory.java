package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

/**
 * Interface for a Factory of game components related to movement.
 */
public interface MovementComponentsFactory {

    /**
     * Handles movement from input.
     * 
     * @return said behaviour as a game component
     */
    GameComponent fromInputMovement();

    /**
     * Handles automatic patrolling movement on X axis.
     * 
     * @return said behaviour as a game component
     */
    GameComponent automaticXMovement();

    /**
     * Handles automatic patrolling movement on Y axis.
     * 
     * @return said behaviour as a game component
     */
    GameComponent automaticYMovement();

}
