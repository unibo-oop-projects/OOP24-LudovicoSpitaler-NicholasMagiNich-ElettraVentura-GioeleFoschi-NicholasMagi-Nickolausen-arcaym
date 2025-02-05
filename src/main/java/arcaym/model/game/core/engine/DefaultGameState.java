package arcaym.model.game.core.engine;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.Rectangle;
import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.score.GameScore;
import arcaym.model.game.score.GameScoreInfo;
import arcaym.model.game.score.UnitGameScore;

/**
 * Default implementation of {@link GameState}.
 */
public class DefaultGameState implements GameState {

    private static final int GAME_SCORE_UNIT = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGameState.class);

    private final GameScore gameScore = new UnitGameScore(GAME_SCORE_UNIT);
    private final Rectangle boundaries;

    /**
     * Initialize game state with boundaries.
     * 
     * @param boundaries total level boundaries
     */
    public DefaultGameState(final Rectangle boundaries) {
        this.boundaries = Objects.requireNonNull(boundaries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber) {
        LOGGER.info("Setting up game score");
        this.gameScore.registerEventsCallbacks(Objects.requireNonNull(eventsSubscriber), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScoreInfo score() {
        return this.gameScore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle boundaries() {
        return this.boundaries;
    }

}
