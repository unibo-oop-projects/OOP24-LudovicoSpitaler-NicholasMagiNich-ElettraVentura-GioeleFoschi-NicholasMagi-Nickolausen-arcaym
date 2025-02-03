package arcaym.controller.game.api;

import arcaym.controller.app.api.Controller;
import arcaym.model.game.core.engine.api.GameStateInfo;

/**
 * Interface for a game controller.
 */
public interface GameController extends Controller { 
    /**
     * starts game.
     */
    void startGame();
    /**
     * 
     * @return game started.
     */
    GameStateInfo getGameState();
}
