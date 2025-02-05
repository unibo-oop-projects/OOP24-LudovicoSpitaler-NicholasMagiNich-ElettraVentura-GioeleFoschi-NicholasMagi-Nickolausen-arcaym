package arcaym.controller.game;

import arcaym.controller.app.Controller;
import arcaym.model.game.core.engine.GameStateInfo;

/**
 * Interface for a game controller.
 */
public interface GameController extends Controller {
    /**
     * 
     * @return game started.
     */
    GameStateInfo getGameState();
}
