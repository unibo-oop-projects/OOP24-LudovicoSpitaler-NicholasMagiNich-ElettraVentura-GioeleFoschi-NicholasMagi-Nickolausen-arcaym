package arcaym.model.game.core.score.impl;

import arcaym.model.game.core.score.api.GameScore;

/**
 * Basic implementation of {@link GameScore.Factory}.
 */
public class GameScoreFactory implements GameScore.Factory {

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
        return this.ofUnit(1);
    }

}
