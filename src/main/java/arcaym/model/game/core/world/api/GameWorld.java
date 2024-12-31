package arcaym.model.game.core.world.api;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.core.world.impl.GameWorldFactory;

/**
 * Interface for a game world.
 */
@TypeRepresentation
public interface GameWorld {

    /**
     * Get game scene.
     * 
     * @return game scene
     */
    @FieldRepresentation
    GameScene scene();

    /**
     * Get game score.
     * 
     * @return game score
     */
    @FieldRepresentation
    GameScore score();

    /**
     * Interface for a {@link GameWorld} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new GameWorldFactory();
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

}
