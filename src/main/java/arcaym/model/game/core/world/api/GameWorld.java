package arcaym.model.game.core.world.api;

import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.score.api.GameScore;

/**
 * Interface for a game world.
 */
public interface GameWorld {

    /**
     * Get game scene.
     * 
     * @return game scene
     */
    GameScene scene();

    /**
     * Get game score.
     * 
     * @return game score
     */
    GameScore score();

}
