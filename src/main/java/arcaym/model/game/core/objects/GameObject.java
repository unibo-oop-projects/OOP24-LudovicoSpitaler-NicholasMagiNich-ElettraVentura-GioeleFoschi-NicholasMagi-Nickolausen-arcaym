package arcaym.model.game.core.objects;

import arcaym.common.geometry.Point;
import arcaym.model.game.core.engine.InteractiveObject;

/**
 * Interface for a basic game object.
 */
public interface GameObject extends InteractiveObject, GameObjectInfo {

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Point position);

}
