package arcaym.model.game.core.scene;

import java.util.Collection;

import arcaym.controller.game.GameUser;
import arcaym.model.game.core.objects.GameObject;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneInfo {

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
