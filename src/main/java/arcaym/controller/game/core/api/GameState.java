package arcaym.controller.game.core.api;

import arcaym.model.game.score.api.GameScoreInfo;

/**
 * Interface for a {@link Game} state.
 */
public interface GameState {

    /**
     * Get game score.
     * 
     * @return game score
     */
    GameScoreInfo score();

}
