package arcaym.model.game.core.scene;

import java.util.Collection;

import arcaym.common.geometry.Point;
import arcaym.controller.game.GameUser;
import arcaym.model.game.core.objects.GameObject;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneInfo {

    /**
     * Schedule creation of an object in the scene.
     * 
     * @param type game object type
     * @param position game object position
     * @param zIndex z index
     */
    default void scheduleCreation(final GameObjectType type, final Point position, final int zIndex) {
        this.scheduleCreation(new CreationInfo(type, position, zIndex));
    }

    /**
     * Schedule creation of an object in the scene.
     * 
     * @param creation creation info
     */
    void scheduleCreation(CreationInfo creation);

    /**
     * Consume pending scene events and notify game user.
     * 
     * @param user game user
     */
    void consumePendingEvents(GameUser user);

    /**
     * {@inheritDoc}
     */
    @Override
    Collection<GameObject> getGameObjects();

}
