package arcaym.controller.game.core.api;

import arcaym.model.game.score.api.GameScoreInfo;

/**
 * Interface for a {@link GameState} restricted view.
 */
public interface GameStateInfo {

    /**
     * Get game score.
     * 
     * @return game score
     */
    GameScoreInfo score();

}
