package arcaym.model.game.core.score.impl;

import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.core.score.api.GameScoreFactory;

/**
 * Basic implementation of {@link GameScoreFactory}.
 */
public class BaseGameScoreFactory implements GameScoreFactory {

    private static final int SIMPLE_SCORE_UNIT_SIZE = 1;

    private int checkValidAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Score manipulation amount must be positive");
        }
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScore ofUnit(final int unit) {
        this.checkValidAmount(unit);
        return new AbstractGameScore() {
            @Override
            public void increment(final int amount) {
                this.changeValue(checkValidAmount(amount) * unit);
            }
            @Override
            public void decrement(final int amount) {
                this.changeValue(-checkValidAmount(amount) * unit);
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameScore simpleScore() {
        return this.ofUnit(SIMPLE_SCORE_UNIT_SIZE);
    }

}
