package arcaym.controller.app.api;

import arcaym.model.game.core.engine.api.Game;

/**
 * The controller for the playable level.
 */
public interface GameController {

    void startGame();

    Game getGame();

    

}
