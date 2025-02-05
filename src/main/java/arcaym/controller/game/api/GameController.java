package arcaym.controller.game.api;

import arcaym.controller.app.api.Controller;
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
