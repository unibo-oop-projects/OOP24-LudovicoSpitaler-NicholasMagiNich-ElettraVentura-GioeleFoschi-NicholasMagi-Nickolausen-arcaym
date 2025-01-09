package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.objects.GameObjectType;

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
     * Get object position.
     * 
     * @return position
     */
    Point getPosition();

}
