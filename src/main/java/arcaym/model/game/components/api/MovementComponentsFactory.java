package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;

/**
 * Interface for a Factory of game components related to movement.
 */
public interface MovementComponentsFactory {

    /**
     * Handles movement from input.
     * 
     * @param gameObject
     * @return said behaviour as a game component
     */
    GameComponent fromInputMovement(UniqueComponentsGameObject gameObject);

    /**
     * Handles automatic patrolling movement on X axis.
     * 
     * @param gameObject
     * @return said behaviour as a game component
     */
    GameComponent automaticXMovement(UniqueComponentsGameObject gameObject);

    /**
     * Handles automatic patrolling movement on Y axis.
     * 
     * @param gameObject
     * @return said behaviour as a game component
     */
    GameComponent automaticYMovement(UniqueComponentsGameObject gameObject);

}
