package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.view.game.api.GameView;

/**
 * Interface for a game scene.
 */
public interface GameScene extends GameSceneInfo {

    /**
     * Consume pending scene events and notify game view.
     * 
     * @param gameView game view
     */
    void consumePendingActions(GameView gameView);

    /**
     * Get all objects in the scene.
     * 
     * @return game objects
     */
    Collection<GameObject> getGameObjects();

}
