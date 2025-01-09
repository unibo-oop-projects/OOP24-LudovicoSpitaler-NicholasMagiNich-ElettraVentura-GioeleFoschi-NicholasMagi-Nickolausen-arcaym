package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.scene.api.GameSceneView;
import arcaym.model.game.core.score.api.GameScore;

/**
 * Interface for a basic game object.
 */
public interface GameObject extends InteractiveObject, GameObjectView {

    /**
     * Get a {@link GameObjectView} on this game object.
     * 
     * @return this object's view
     */
    default GameObjectView view() {
        return this;
    }

    /**
     * Get game scene of the object.
     * 
     * @return game scene
     */
    GameSceneView scene();

    /**
     * Get game score used by the object.
     * 
     * @return game score
     */
    GameScore score();

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
