package arcaym.controller.game.core.api;

import arcaym.model.game.score.api.GameScoreView;

/**
 * Interface for a {@link Game} state.
 */
public interface GameState {

    /**
     * Get game score.
     * 
     * @return game score
     */
    GameScoreView score();

}
