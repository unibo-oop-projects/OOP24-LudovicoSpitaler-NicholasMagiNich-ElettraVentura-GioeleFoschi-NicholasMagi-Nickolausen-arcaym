package arcaym.model.game.core.objects.api;

import arcaym.common.position.api.Position;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a basic game object.
 */
public interface GameObject extends InteractiveObject {

    /**
     * Get the specific type of the object.
     * 
     * @return game object type
     */
    GameObjectType type();

    /**
     * Get the major category of the object.
     * 
     * @return game object category
     */
    default GameObjectCategory category() {
        return this.type().category();
    }

    /**
     * Get world associated with the object.
     * 
     * @return the game world
     */
    GameWorld world();

    /**
     * Get object position.
     * 
     * @return position
     */
    Position getPosition();

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Position position);

    /**
     * Move object from current position by distance.
     * 
     * @param distance amount to move on each coordinate
     */
    void move(Position distance);

}
