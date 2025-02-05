package arcaym.model.game.core.engine;

import arcaym.common.geometry.Rectangle;
import arcaym.model.game.score.GameScoreInfo;

/**
 * Interface for a {@link GameState} restricted view.
 */
public interface GameStateInfo {

    /**
     * @return game score
     */
    GameScoreInfo score();

    /**
     * @return total level boundaries
     */
    Rectangle boundaries();

}
