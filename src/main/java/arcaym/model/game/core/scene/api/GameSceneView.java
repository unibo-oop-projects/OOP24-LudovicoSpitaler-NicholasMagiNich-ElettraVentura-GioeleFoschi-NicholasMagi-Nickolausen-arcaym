package arcaym.model.game.core.scene.api;

import java.util.Set;

import arcaym.model.game.core.objects.api.GameObjectView;

/**
 * Interface for a {@link GameScene} restricted view.
 */
public interface GameSceneView {

    /**
     * Get all objects in the scene.
     * 
     * @return game objects
     */
    Set<GameObjectView> getObjects();

}
