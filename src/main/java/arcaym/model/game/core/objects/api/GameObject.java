package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.world.api.GameWorld;

/**
 * Interface for a basic game object.
 */
@TypeRepresentation
public interface GameObject extends InteractiveObject, GameObjectView {

    /**
     * Get world associated with the object.
     * 
     * @return the game world
     */
    @FieldRepresentation
    GameWorld world();

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
    void move(Point distance);

}
