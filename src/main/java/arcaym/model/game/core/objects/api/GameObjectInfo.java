package arcaym.model.game.core.objects.api;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link GameObject} info view.
 */
public interface GameObjectInfo {

    /**
     * Get the specific type of the object.
     * 
     * @return game object type
     */
    GameObjectType type();

    /**
     * Get the major category of the object's type.
     * 
     * @return game object's type category
     */
    default GameObjectCategory category() {
        return this.type().category();
    }

    /**
     * Get object position.
     * 
     * @return position
     */
    Point getPosition();

    /**
     * Get object boundaries.
     * 
     * @return boundaries
     */
    Rectangle boundaries();

}
