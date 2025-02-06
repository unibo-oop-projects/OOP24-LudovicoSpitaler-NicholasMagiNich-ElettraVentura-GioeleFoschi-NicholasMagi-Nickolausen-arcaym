package arcaym.model.game.core.objects;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Rectangle;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameObject} info view.
 */
public interface GameObjectInfo {

    /**
     * @return object type
     */
    GameObjectType type();

    /**
     * @return {@link #type()} category
     */
    default GameObjectCategory category() {
        return this.type().category();
    }

    /**
     * @return position
     */
    Point getPosition();

    /**
     * @return boundaries
     */
    Rectangle boundaries();

    /**
     * @return z index
     */
    int zIndex();

}
