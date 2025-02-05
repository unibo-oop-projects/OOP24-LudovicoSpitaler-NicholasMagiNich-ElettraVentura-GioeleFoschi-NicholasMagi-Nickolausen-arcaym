package arcaym.model.game.components.api;

import arcaym.model.game.core.components.ComponentsBasedGameObject;
import arcaym.model.game.core.components.GameComponent;

/**
 * Interface for a Factory of game components related to movement.
 */
public interface MovementComponentsFactory {

    /**
     * Handles movement from input.
     * 
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent fromInputMovement(ComponentsBasedGameObject gameObject);

    /**
     * Handles automatic patrolling movement on X axis.
     * 
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent automaticXMovement(ComponentsBasedGameObject gameObject);

    /**
     * Handles automatic patrolling movement on Y axis.
     * 
     * @param gameObject game object
     * @return said behaviour as a game component
     */
    GameComponent automaticYMovement(ComponentsBasedGameObject gameObject);

}
