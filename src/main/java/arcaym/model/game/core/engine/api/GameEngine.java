package arcaym.model.game.core.engine.api;

/**
 * Interface for a game update loop manager.
 */
public interface GameEngine {

    /**
     * Start game update loop.
     */
    void startGame();

    /**
     * Stop game update loop.
     */
    void stopGame();

}
