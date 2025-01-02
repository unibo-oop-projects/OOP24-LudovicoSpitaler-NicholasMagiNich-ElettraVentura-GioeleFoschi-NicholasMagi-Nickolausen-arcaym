package arcaym.model.game.core.world.api;

import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.core.world.impl.BaseGameWorldFactory;

/**
 * Interface for a {@link GameWorld} factory.
 */
public interface GameWorldFactory {

    /**
     * Get new instance of the default factory implementation.
     * 
     * @return factory instance
     */
    static GameWorldFactory newDefault() {
        return new BaseGameWorldFactory();
    }

    /**
     * Create world with given scene and score.
     * 
     * @param scene game scene
     * @param score game score
     * @return resulting world
     */
    GameWorld ofSceneAndScore(GameScene scene, GameScore score);

}
