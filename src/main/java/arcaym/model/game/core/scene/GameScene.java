package arcaym.model.game.core.scene;

import java.util.Collection;

import arcaym.controller.game.GameObserver;
import arcaym.model.game.core.objects.GameObject;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneInfo {

    /**
     * Consume pending scene events and notify game observer.
     * 
     * @param observer game observer
     */
    void consumePendingActions(GameObserver observer);

    /**
     * {@inheritDoc}
     */
    @Override
    Collection<GameObject> getGameObjects();

}
