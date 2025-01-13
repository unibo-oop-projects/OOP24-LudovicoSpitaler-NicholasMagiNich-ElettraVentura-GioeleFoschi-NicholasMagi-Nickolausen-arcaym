package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.objects.api.GameObjectType;

/**
 * Interface for a {@link GameObject} restricted view.
 */
public interface GameObjectView {

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

}
