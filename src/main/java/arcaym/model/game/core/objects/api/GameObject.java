package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.engine.api.InteractiveObject;

/**
 * Interface for a basic game object.
 */
public interface GameObject extends InteractiveObject, GameObjectView {

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Point position);

    /**
     * Move object from current position by distance.
     * 
     * @param distance amount to move on each coordinate
     */
    default void move(final Point distance) {
        this.setPosition(this.getPosition().sum(distance));
    }

}
