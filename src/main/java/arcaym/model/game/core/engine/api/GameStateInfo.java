package arcaym.model.game.core.engine.api;

import arcaym.common.geometry.impl.Rectangle;
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

    /**
     * Get rectangle containing the entire level.
     * 
     * @return boundaries rectangle
     */
    Rectangle boundaries();

}
