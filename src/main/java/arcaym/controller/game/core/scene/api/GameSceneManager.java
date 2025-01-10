package arcaym.controller.game.core.scene.api;

/**
 * Interface for a {@link GameScene} manager.
 */
public interface GameSceneManager extends GameScene {
    
    /**
     * Consume pending scene events.
     */
    void consumePendingActions();

}
