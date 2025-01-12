package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.score.api.GameScore;
import arcaym.model.game.score.api.GameScoreView;
import arcaym.model.game.score.impl.UnitGameScore;

/**
 * Default implementation of {@link GameState}.
 */
public class DefaultGameState implements GameState {

    private static final int GAME_SCORE_UNIT = 100;

    private final GameScore score = new UnitGameScore(GAME_SCORE_UNIT);

    DefaultGameState(final EventsSubscriber<GameEvent> gameEventsSubscriber) {
        this.score.registerEventsCallbacks(Objects.requireNonNull(gameEventsSubscriber));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScoreView score() {
        return this.score;
    }

}
