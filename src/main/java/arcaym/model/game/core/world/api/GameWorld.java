package arcaym.model.game.core.world.api;

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
