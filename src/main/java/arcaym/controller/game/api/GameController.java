package arcaym.controller.game.api;

import arcaym.controller.app.api.Controller;
import arcaym.model.game.core.engine.api.GameObserver;
import arcaym.model.game.core.engine.api.GameStateInfo;

/**
 * Interface for a game controller.
 */
public interface GameController extends Controller, GameObserver {
    /**
     * 
     * @return game started.
     */
    GameStateInfo getGameState();
}
