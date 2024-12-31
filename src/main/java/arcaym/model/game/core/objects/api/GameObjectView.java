package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameObject} restricted view.
 */
@TypeRepresentation
public interface GameObjectView {

    /**
     * Get the specific type of the object.
     * 
     * @return game object type
     */
    @FieldRepresentation
    GameObjectType type();

    /**
     * Get the major category of the object.
     * 
     * @return game object category
     */
    @FieldRepresentation
    default GameObjectCategory category() {
        return this.type().category();
    }

    /**
     * Get object position.
     * 
     * @return position
     */
    @FieldRepresentation
    Point getPosition();

}
