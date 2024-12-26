package arcaym.model.game.core.world.api;

/**
 * Interface for a game world.
 * It manages access for all game objects to score, events, inputs and other
 * objects.
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

    /**
     * Get in-game events manager.
     * 
     * @return game events manager
     */
    EventsManager<GameEvent> gameEvents();

    /**
     * Get user inputs events manager.
     * 
     * @return input events manager
     */
    EventsManager<InputEvent> inputEvents();

}
