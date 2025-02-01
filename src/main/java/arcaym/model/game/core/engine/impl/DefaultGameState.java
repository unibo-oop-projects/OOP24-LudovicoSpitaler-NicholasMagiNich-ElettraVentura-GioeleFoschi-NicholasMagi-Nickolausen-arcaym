package arcaym.model.game.core.engine.impl;

import java.util.Objects;

import arcaym.model.game.core.engine.api.GameState;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.score.api.GameScore;
import arcaym.model.game.score.api.GameScoreInfo;
import arcaym.model.game.score.impl.UnitGameScore;

/**
 * Default implementation of {@link GameState}.
 */
public class DefaultGameState implements GameState {

    private static final int GAME_SCORE_UNIT = 100;

    private final GameScore gameScore = new UnitGameScore(GAME_SCORE_UNIT);

    /**
     * {@inheritDoc}
     */
    @Override
    public void setupCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber) {
        this.gameScore.registerEventsCallbacks(Objects.requireNonNull(eventsSubscriber), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScoreInfo score() {
        return this.gameScore;
    }

}
